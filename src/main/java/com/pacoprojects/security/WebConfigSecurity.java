package com.pacoprojects.security;

import com.pacoprojects.service.ImpUserDetailsService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/* Mapeia URL, enderecos, autoriza ou bloqueia acesso a URL*/
@EnableWebSecurity
@AllArgsConstructor
public class WebConfigSecurity extends WebSecurityConfigurerAdapter {

    private final ImpUserDetailsService impUserDetailsService;

    private final JWTFilter jwtFilter;

    private final JWTAutenticacaoService jwtAutenticacaoService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /* Service que implementa a interface UserDetailsService, e seu metodo loadUserByUsername apos isso criptografa o password*/
        auth.userDetailsService(impUserDetailsService)
                /* Padrao de criptografia de senha */
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    /* Configura as solicitacoes de acesso Http */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /* Ativando a protecao contra usuarios que nao estao validados por token */
        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).disable()
                /* Ativando a permissao para acesso a pagina inicial do sistema sem precisar Logar */
                .authorizeRequests().antMatchers("/", "/index","/endereco").permitAll()
                .antMatchers(HttpMethod.POST,"/usuario").permitAll()
                /* Qualquer outra URL precisara de estar logado */
                .anyRequest().authenticated()
                /* URL de Logout - Redireciona apos o user deslogar do sistema para o index.html */
                .and().logout().logoutSuccessUrl("/index")
                /* Mapeia URL de Logout e invalida o usuario */
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                /* TODO filtra as demais requisicoes se contem a presenca do JWT no HEADER HTTP */
                .and().addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                /* TODO filtra as requisicoes de login para autenticacao */
                .addFilter(new JWTLoginFilter(authenticationManager(), jwtAutenticacaoService));
    }

    @Bean
    public WebMvcConfigurer getCorsConfigurer() {

        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/**") /* Liberando Cors para qualquer end-point */
                        .allowedOrigins("*") /* Liberando Cors de qualquer local(ajax, navegador, etc...) */
                        .allowedMethods("*") /* Liberando Cors para qualquer metodo GET,POST,PUT,DELETE */
                        .allowedHeaders("*");
            }
        };
    }
}
