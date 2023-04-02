package com.pacoprojects.recovery;

import com.pacoprojects.email.EmailConfig;
import com.pacoprojects.email.EmailObject;
import com.pacoprojects.email.EmailService;
import com.pacoprojects.model.Usuario;
import com.pacoprojects.repository.UsuarioRepository;
import com.pacoprojects.security.ApplicationConfig;
import com.pacoprojects.security.jwt.JwtUtilService;
import com.pacoprojects.util.Constantes;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecoveryService {

    private final UsuarioRepository usuarioRepository;
    private final EmailService emailService;
    private final EmailConfig emailConfig;
    private final JwtUtilService jwtUtilService;
    private final ApplicationConfig applicationConfig;

    public void sendEmailRecoveryPassword(RecoveryRequestEmailDto requestEmailDto) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findUsuarioByUsername(requestEmailDto.email());

        if (optionalUsuario.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constantes.USERNAME_NOTFOUND_EXCEPTION);
        }

        String basicToken = jwtUtilService.generateTokenOneDayUntilExpiration(optionalUsuario.get());

        final String link = emailConfig.getRecoveryUrl() + basicToken;

        emailService.sendMailWithAttachment(
                EmailObject
                        .builder()
                        .recipient(requestEmailDto.email())
                        .subject(Constantes.SUBJECT)
                        .msgBody(Constantes.BODY + link)
                        .build());
    }

    public Boolean validateBasicTokenExpired(RecoveryRequestValidateToken requestValidateToken) {

        try {
            return jwtUtilService.isTokenExpired(requestValidateToken.basicToken());
        } catch (Exception exception) {
            if (exception instanceof ExpiredJwtException) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constantes.RESET_TOKEN_EXPIRED);
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constantes.RESET_TOKEN_INVALID);
        }
    }

    public void resetPassword(RecoveryRequestNewPasswordDto requestNewPasswordDto) {

        validateBasicTokenExpired(RecoveryRequestValidateToken
                .builder()
                .basicToken(requestNewPasswordDto.basicToken())
                .build());

        String username = jwtUtilService.extractUsername(requestNewPasswordDto.basicToken());

        Optional<Usuario> optionalUsuario = usuarioRepository.findUsuarioByUsername(username);

        if (optionalUsuario.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constantes.USERNAME_NOTFOUND_EXCEPTION);
        }

        if (applicationConfig.passwordEncoder().matches(requestNewPasswordDto.newPassword(), optionalUsuario.get().getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constantes.PASSWORD_ALREADY_IN_USE);
        }

        String novaSenhaCriptografada = applicationConfig.passwordEncoder().encode(requestNewPasswordDto.newPassword());
        optionalUsuario.get().setPassword(novaSenhaCriptografada);
        usuarioRepository.save(optionalUsuario.get());
    }
}
