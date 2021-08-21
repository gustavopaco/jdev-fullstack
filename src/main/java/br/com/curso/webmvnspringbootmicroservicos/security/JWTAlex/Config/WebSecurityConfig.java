package br.com.curso.webmvnspringbootmicroservicos.security.JWTAlex.Config;

import br.com.curso.webmvnspringbootmicroservicos.repository.UsuarioRepository;
import br.com.curso.webmvnspringbootmicroservicos.security.JWTAlex.JWTApiAutenticacaoFilter;
import br.com.curso.webmvnspringbootmicroservicos.security.JWTAlex.JWTLoginFilter;
import br.com.curso.webmvnspringbootmicroservicos.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static org.springframework.http.HttpMethod.POST;

@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /* IMPORTANT: 2/2 ETAPAS para desabilitar o Cors*/
        http.cors();
        http.headers().addHeaderWriter((request, response) -> response.setHeader("Access-Control-Allow-Origin", "*"));
        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).disable().authorizeRequests()
                    .antMatchers("/", "/actuator/**","/profile/**", "/telefone/**", "/address/**").permitAll()
                    .antMatchers(POST,"/usuario").hasAnyRole("ANONYMOUS")
                    .antMatchers("/usuario/**").hasAnyRole("ADMIN")
                    .anyRequest().authenticated()
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
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
