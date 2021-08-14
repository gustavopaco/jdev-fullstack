package br.com.curso.webmvnspringbootmicroservicos.security;

import br.com.curso.webmvnspringbootmicroservicos.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UsuarioService usuarioService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                    .antMatchers("/","/usuario/**","/actuator/**","/profile/**", "/telefone/**")
                    .permitAll()
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .permitAll()
                .and()
                .logout()
                    .permitAll();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usuarioService).passwordEncoder(new BCryptPasswordEncoder());

//        auth
//                .inMemoryAuthentication()
//                .passwordEncoder(new BCryptPasswordEncoder())
//                .withUser("admin")
//                .password("$2a$10$px23nFDTwDkZ6gEsE7lHDOf1cjbECHxOLPQaFlKuiUdMiHsvRZ6mm");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/static/**");
    }
}
