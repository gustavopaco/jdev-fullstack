package br.com.webmvnspringbootthymeleaf.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter {

    @Override /* Configura as solicitacoes de acesso por Http */
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable() /* Desativa as configuracoes padroes de memoria do Spring Security */
                .authorizeRequests() /* Permite e restringe acessos */
                .antMatchers(HttpMethod.GET, "/").permitAll() /* Qualquer usuario acessa a pagina inicial */
                .anyRequest().authenticated()
                .and().formLogin().permitAll() /* Cria Formulario de login permite a qualquer usuario acessar */
                .and().logout() /* Mapeia URL de Logout e invalida usuario autenticado */
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
    }

    @Override /* Cria autenticacao do usuario com o banco de dados ou em memoria */
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()) /* Aqui esta setando a senha somente em texto nao esta criptografando ainda */
                .withUser("gustavopaco")
                .password("$2a$10$px23nFDTwDkZ6gEsE7lHDOf1cjbECHxOLPQaFlKuiUdMiHsvRZ6mm")
                .roles("ADMIN");
    }

    @Override /* Ignora URL especificas */
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/css/**")
        .and().ignoring().antMatchers("/js/**");
    }
}
