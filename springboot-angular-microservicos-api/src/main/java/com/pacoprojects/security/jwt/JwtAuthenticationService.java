package com.pacoprojects.security.jwt;

import com.pacoprojects.model.Usuario;
import com.pacoprojects.repository.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

import static com.pacoprojects.util.Constantes.*;

@Service
@RequiredArgsConstructor
public class JwtAuthenticationService {

    private final UsuarioRepository usuarioRepository;
    private final JwtUtilService jwtUtilService;

    public void getAuthentication(HttpServletRequest request) {
        Map<String, Object> objectMap = jwtUtilService.breakToken(request);

        if (!objectMap.isEmpty()) {
            String username = objectMap.get(USERNAME).toString();
            String basicToken = objectMap.get(BASICTOKEN).toString();

            Optional<Usuario> usuarioOptional = usuarioRepository.findUsuarioByUsername(username);

            if (usuarioOptional.isPresent() && usuarioOptional.get().getJwt().equals(basicToken)) {
                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(username, null, usuarioOptional.get().getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                throw new AuthorizationServiceException(AUTHORIZATION_SERVICE_EXCEPTION_MESSAGE);
            }
        }
    }
}
