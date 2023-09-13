package com.pacoprojects.security;

import com.pacoprojects.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final ApplicationConfig applicationConfig;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                /* REST - Desabilitando CSRF*/
                .csrf().disable()
                /* Aplicando as configuracoes do @Bean Cors */
                .cors()
                .and()
                /* Configurando Session Spring para Stateless */
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                /* Adicionando Filtro de getAuthentication e Autorizacoes ANTES do Spring verificar Usuario Logado */
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                /* Urls liberadas */
                .authorizeHttpRequests()
                .requestMatchers("/error","/auth/**", "/profissoes/**", "/recovery/**").permitAll()
                .requestMatchers("/usuario").hasAnyRole("ADMIN")
                /* Qualquer outra Url bloqueada */
                .anyRequest()
                .authenticated()
                .and()
                /* Fornecedor de Autenticacao */
                .authenticationProvider(applicationConfig.authenticationProvider());

        return http.build();
    }
}
