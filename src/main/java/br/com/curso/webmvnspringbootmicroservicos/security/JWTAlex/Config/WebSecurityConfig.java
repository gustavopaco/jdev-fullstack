package br.com.curso.webmvnspringbootmicroservicos.security.JWTAlex.Config;

import br.com.curso.webmvnspringbootmicroservicos.repository.UsuarioRepository;
import br.com.curso.webmvnspringbootmicroservicos.security.JWTAlex.JWTApiAutenticacaoFilter;
import br.com.curso.webmvnspringbootmicroservicos.security.JWTAlex.JWTLoginFilter;
import br.com.curso.webmvnspringbootmicroservicos.service.UsuarioService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /* IMPORTANT: 2/2 ETAPAS para desabilitar o Cors*/
        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).disable();

        http.headers().addHeaderWriter((request, response) -> {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Headers","*");
            response.setHeader("Access-Control-Request-Headers","*");
            response.setHeader("Access-Control-Max-Age","3600");
            response.setHeader("Access-Control-Allow-Methods","*");
        });
        http.cors()
                .and()
                    .authorizeRequests()
                    .antMatchers("/", "/actuator/**","/profile/**", "/telefone/**", "/address/**", "/profissao/**", "/recovery/**", "/role/**").permitAll()
                    .antMatchers(HttpMethod.GET, "/usuario/**").hasAnyRole("ADMIN")
                    .antMatchers(HttpMethod.POST,"/usuario/**").hasAnyRole("ADMIN","ANONYMOUS")
                    .antMatchers(HttpMethod.PUT, "/usuario/**").hasAnyRole("ADMIN")
                    .antMatchers(HttpMethod.DELETE, "/usuario/**").hasAnyRole("ADMIN")
                    .antMatchers(HttpMethod.OPTIONS, "/usuario/**").hasAnyRole("ADMIN")
                    .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .and()
                // IMPORTANT: Adicionando filtro para as requisicoes de autenticacao
                .addFilterBefore(new JWTLoginFilter("/login",authenticationManagerBean(), usuarioRepository),
                        UsernamePasswordAuthenticationFilter.class)
                //  IMPORTANT: Filtra as demais requisicoes para verificar a presenca do TOKEN JWT no Header JWT
                .addFilterBefore(new JWTApiAutenticacaoFilter(usuarioRepository), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usuarioService).passwordEncoder(bCryptPasswordEncoder);

            // auth
                // .inMemoryAuthentication()
                // .passwordEncoder(new BCryptPasswordEncoder())
                // .withUser("admin")
                // .password("$2a$10$px23nFDTwDkZ6gEsE7lHDOf1cjbECHxOLPQaFlKuiUdMiHsvRZ6mm");
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/static/**");
    }

    @Bean
    public WebMvcConfigurer getCorsConfiguration() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("*")
                        .allowedHeaders("*");
            }
        };
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
