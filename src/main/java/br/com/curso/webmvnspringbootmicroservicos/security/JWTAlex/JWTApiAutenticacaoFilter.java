package br.com.curso.webmvnspringbootmicroservicos.security.JWTAlex;

import br.com.curso.webmvnspringbootmicroservicos.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

// IMPORTANT: Filtro onde todas as requisicoes sao capturadas para autenticar
@AllArgsConstructor
public class JWTApiAutenticacaoFilter extends OncePerRequestFilter {

    private final UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest  request, @NonNull HttpServletResponse response, @NonNull FilterChain chain) throws ServletException, IOException {

        try {
            Authentication authentication = new JWTTokenAutenticacaoService(usuarioRepository).getAuthentication(request, response);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            HashMap<String, String> map = new HashMap<>();
            map.put("error", e.getMessage());
            response.addHeader("error-messsage", e.getMessage());
            response.setStatus(FORBIDDEN.value());
            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), map);
        }
        chain.doFilter(request, response);

//            if (request.getServletPath().equals("/login")) {
//                // Continuando o processo
//                chain.doFilter(request, response);
//            } else {
//                // Buscando autenticacao do servico
//                Authentication authentication = new JWTTokenAutenticacaoService(usuarioRepository).getAuthentication(request);
//                if (authentication != null) {
//                    // Setando no contexto do Spring Security a autenticacao interceptada pelo Filtro
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                    chain.doFilter(request, response);
//                }
//                chain.doFilter(request, response);
//            }
    }
}
