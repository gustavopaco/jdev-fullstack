package com.pacoprojects.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.pacoprojects.util.Constantes.EXPIRED_JWT_EXCEPTION_MESSAGE;
import static com.pacoprojects.util.Constantes.MALFORMED_JWT_EXCEPTION_MESSAGE;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtAuthenticationService jwtAuthenticationService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull FilterChain filterChain) throws ServletException, IOException {

        try {
            jwtAuthenticationService.getAuthentication(request);
            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            Map<String, String> map = new HashMap<>();
            if (exception instanceof AuthorizationServiceException) {
                map.put("error", exception.getMessage());
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            } else if (exception instanceof ExpiredJwtException) {
                map.put("error", EXPIRED_JWT_EXCEPTION_MESSAGE);
                response.setStatus(HttpStatus.FORBIDDEN.value());
            } else if (exception instanceof MalformedJwtException) {
                map.put("error", MALFORMED_JWT_EXCEPTION_MESSAGE);
                response.setStatus(HttpStatus.FORBIDDEN.value());
            } else {
                map.put("error", exception.getMessage());
                response.setStatus(HttpStatus.FORBIDDEN.value());
            }
            new ObjectMapper().writeValue(response.getOutputStream(),map);
        }
    }
}
