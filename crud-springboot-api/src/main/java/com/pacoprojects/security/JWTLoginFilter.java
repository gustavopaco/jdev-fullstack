package com.pacoprojects.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.pacoprojects.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTAutenticacaoService jwtAutenticacaoService;

    /* Retorna o Usuario ao processar autenticacao*/
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        Usuario usuario = null;
        try {
            /* Obtem dados da requisicao e tenta injetar dentro de um Objeto da classe Usuario */
            usuario = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        /* Seta dados do usuario e por baixo dos panos chama metodo loadUserByUsername(String username) para verificar se existe no banco */
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usuario.getUsername(), usuario.getPassword(), usuario.getAuthorities()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        /* Recebendo o objeto caso autenticacao com sucesso*/
        User user = (User) authentication.getPrincipal();

        /* Chamando Metodo que: Gera Token de Autenticacao | Salva Token gerado no Banco | Adiciona Token gerado no Header da resposta para Usuario*/
        try {
            jwtAutenticacaoService.setAuthentication(response, user.getUsername());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        /* Se autenticacao falhou, o Spring mapeia o error e manda como um objeto Error: Object(mensagem do error) */
        Map<String,String> map = new HashMap<>();
        map.put("error",failed.getMessage());
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(),map);
    }
}
