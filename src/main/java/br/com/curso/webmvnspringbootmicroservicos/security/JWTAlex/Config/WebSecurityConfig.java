package br.com.curso.webmvnspringbootmicroservicos.security.JWTAlex.Config;

import br.com.curso.webmvnspringbootmicroservicos.repository.UsuarioRepository;
import br.com.curso.webmvnspringbootmicroservicos.security.JWTAlex.JWTApiAutenticacaoFilter;
import br.com.curso.webmvnspringbootmicroservicos.security.JWTAlex.JWTLoginFilter;
import br.com.curso.webmvnspringbootmicroservicos.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
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
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).disable().authorizeRequests()
                    .antMatchers("/", "/actuator/**","/profile/**", "/telefone/**").permitAll()
                    .antMatchers(HttpMethod.POST,"/usuario").permitAll()
                    .antMatchers("/usuario/**").hasAnyRole("ADMIN")
                    .anyRequest().authenticated()
                .and()
                // IMPORTANT: Adicionando filtro para as requisicoes de autenticacao
                .addFilterBefore(new JWTLoginFilter("/login",
                        authenticationManager(), usuarioRepository),
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

    // IMPORTANT: CONFIGURACAO GLOBAL Cors - Aceita requisoes AJAX vindo de IP-URLs diferentes onde API esta hospedada
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
        // Mapeamento customizado
        // registry.addMapping("/**")
            // .allowedMethods("GET","POST","PUT","DELETE")
            // .allowedOrigins("www.servidor1.com","www.empresa1.com");
    }
}
