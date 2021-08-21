package br.com.curso.webmvnspringbootmicroservicos.security.JWTAlex;

import br.com.curso.webmvnspringbootmicroservicos.model.Usuario;
import br.com.curso.webmvnspringbootmicroservicos.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

// IMPORTANT: Classe de gerenciador de Token
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    private final UsuarioRepository usuarioRepository;

    // Important: Configurando o construtor da classe
    public JWTLoginFilter(String url, AuthenticationManager authenticationManager, UsuarioRepository usuarioRepository) {
        super(url, authenticationManager);
        this.usuarioRepository = usuarioRepository;
        // /*Obrigando ao construtor autenticar a URL
        // super(new AntPathRequestMatcher(url));
        // Gerenciador de Autenticacao
        // setAuthenticationManager(authenticationManager);*/
    }

    // IMPORTANT: Retorna oo usuario ao processar a autenticacao
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {

        Usuario usuario = new ObjectMapper()
                .readValue(request.getInputStream(), Usuario.class);

        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(usuario.getUsername(), usuario.getPassword(), usuario.getAuthorities()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        User usuario = (User) authentication.getPrincipal();
        new JWTTokenAutenticacaoService(usuarioRepository).addAuthentication(request, response, usuario);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        HashMap<String, String> map = new HashMap<>();
        map.put("error", failed.getMessage());
        // response.setHeader("error_message", failed.getMessage());
        // corsConfiguration.addCorsConfiguration(response);
        response.setStatus(FORBIDDEN.value());
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), map);
    }
}
