package com.pacoprojects.auth;

import com.pacoprojects.mapper.UsuarioMapper;
import com.pacoprojects.model.Role;
import com.pacoprojects.repository.RoleRepository;
import com.pacoprojects.security.ApplicationConfig;
import com.pacoprojects.security.jwt.JwtConfig;
import com.pacoprojects.security.jwt.JwtUtilService;
import com.pacoprojects.model.Usuario;
import com.pacoprojects.repository.UsuarioRepository;
import com.pacoprojects.util.BeanValidator;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static com.pacoprojects.util.Constantes.*;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final ApplicationConfig applicationConfig;
    private final AuthenticationManager authenticationManager;
    private final UsuarioMapper usuarioMapper;
    private final JwtUtilService jwtUtilService;
    private final JwtConfig jwtConfig;
    private final BeanValidator beanValidator;
    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;

    public AuthenticationResponseDto authenticate(AuthenticationRequestDto authenticationRequestDto, BindingResult bindingResult, HttpServletResponse response) {
        beanValidator.validate(bindingResult);

        try {
            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(authenticationRequestDto.username(), authenticationRequestDto.password()));

            return authenticateRegister((Usuario)authentication.getPrincipal(),response);

        }catch (Exception exception) {
            if (exception instanceof BadCredentialsException) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, BAD_CREDENTIALS_EXCEPTION_MESSAGE);
            } else if (exception instanceof LockedException) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, LOCKED_EXCEPTION_MESSAGE);
            }
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MESSAGE);
        }
    }

    @CacheEvict(cacheNames = {"usuarios"}, allEntries = true)
    public AuthenticationResponseDto register(RegisterDto registerDto, BindingResult bindingResult, HttpServletResponse response) {

        beanValidator.validate(bindingResult);

        Optional<Usuario> optionalUsuario = usuarioRepository.findUsuarioByUsername(registerDto.username());

        if (optionalUsuario.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, EMAIL_TAKEN);
        }
        Usuario usuario = usuarioMapper.toEntity2(registerDto);

        Optional<Role> optionalRole = roleRepository.findByAuthority("ROLE_FISICA");
        if (optionalRole.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ROLE_NOTFOUND_IN_DB);
        }
        usuario.getAuthorities().add(optionalRole.get());
        usuario.setPassword(applicationConfig.passwordEncoder().encode(usuario.getPassword()));

        return authenticateRegister(usuario, response);
    }

    private AuthenticationResponseDto authenticateRegister(Usuario usuario, HttpServletResponse response) {

        String basicToken = jwtUtilService.generateToken(usuario);
        usuario.setJwt(basicToken);
        usuarioRepository.save(usuario);

        String fullToken = jwtConfig.getPrefixToken().concat(basicToken);
        usuario.setJwt(fullToken);
        response.addHeader(HttpHeaders.AUTHORIZATION, fullToken);
        return usuarioMapper.toDto(usuario);
    }
}
