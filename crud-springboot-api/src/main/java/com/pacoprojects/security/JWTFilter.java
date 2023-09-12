package com.pacoprojects.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTAutenticacaoService jwtAutenticacaoService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        /* Filtra as requisicoes para /login e deixa continuar*/
        if (request.getServletPath().equals("/login")) {
            filterChain.doFilter(request, response);
        } else {
            try {
                /* Chamando Metodo que: Quebra o Token | Busca Usuario no Banco | Retorna Usuario e suas Permissoes OU USUARIO NULL*/
                Authentication authentication = jwtAutenticacaoService.getAuthentication(request);
                if (authentication != null) {
                    /* Se existe permissoes no token, entao Seta usuario e suas permissoes no contexto do Spring Security e deixa continuar */
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    filterChain.doFilter(request, response);
                } else {
                    filterChain.doFilter(request, response);
                }
            } catch (Exception exception) {
                Map<String, String> map = new HashMap<>();
                if (exception instanceof ExpiredJwtException || exception instanceof AuthorizationServiceException) {
                    map.put("error", "Sessao invalida, por favor faça o login novamente.");
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                } else if (exception instanceof MalformedJwtException) {
                    map.put("error", "Token invalido, por favor faça o login novamente");
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                } else {
                    map.put("error", exception.getMessage());
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                }
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), map);
            }
        }
    }
}
