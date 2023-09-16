package com.pacoprojects.service;

import com.pacoprojects.model.Usuario;
import com.pacoprojects.repository.UsuarioRepository;
import com.pacoprojects.security.JWTAlex.JWTTokenAutenticacaoService;
import com.pacoprojects.util.JavaGenericMail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.pacoprojects.model.Constantes.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@AllArgsConstructor @Slf4j
@Service
public class UserRecoveryService {

    private static final long EXPIRATION_TIME = 10 * 60 * 1000;
    private final JavaGenericMail javaGenericMail;
    private final UsuarioRepository usuarioRepository;
    private JWTTokenAutenticacaoService jwtTokenAutenticacaoService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public ResponseEntity<?> recoveryEmail(Usuario usuario, HttpServletRequest request) {

        Usuario usuarioConsultado = usuarioRepository.findUsuarioByLogin(usuario.getUsername());

        if (usuarioConsultado == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario nao foi encontrado");
        }

        Map<String, Object> objectMap = jwtTokenAutenticacaoService.generateTokenUser(request, usuario, EXPIRATION_TIME);
        String tokenFormatado = (String) objectMap.get("tokenFormatado");

        usuarioConsultado.setRecoveryKey(tokenFormatado);
        usuarioRepository.save(usuarioConsultado);

        /* IMPORTANT: Disparando EMAIL para UsuarioConsultado */
        List<String> destinatarios = new ArrayList<>();
        destinatarios.add(usuario.getUsername());
        String conteudo = "Hello, you are trying to reset your password, if you want to continue, please click on the link below: \n" + ANGULARURL.getValue() + tokenFormatado;

        try {
            javaGenericMail.enviarEmail(FROM.getValue(),
                                        USERNAME.getValue(),
                                        PASSWORD.getValue(),
                                        FROMNAME.getValue(),
                                        destinatarios,
                                        ASSUNTO.getValue(),
                                        conteudo,
                                false,
                                        new ArrayList<>(),
                                        SMTP.getValue());
        } catch (Exception e) {
            log.error("Erro ao sair do metodo do Email...", e.getCause());
        }

        return ResponseEntity.ok().build();
    }


    public ResponseEntity<?> validationToken(HttpServletRequest request) {
        try {
            jwtTokenAutenticacaoService.breakToken(request);
            return ResponseEntity.ok().build();
        } catch (Exception exception) {
            throw new ResponseStatusException(BAD_REQUEST, "Expired link");
        }
    }

    /* TODO: FAZER METODO DE ALTERAR PASSWORD DO USUARIO E REDIRECIONA-LO PARA TELA DE LOGIN */
    public ResponseEntity<?> updatePassword(Usuario usuario, HttpServletRequest request) {
        try {
            Map<String, Object> objectMap = jwtTokenAutenticacaoService.breakToken(request);

            String username = (String) objectMap.get("username");

            Usuario usuarioConsultado = usuarioRepository.findUsuarioByLogin(username);

            usuarioConsultado.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));

            usuarioRepository.save(usuarioConsultado);

            return ResponseEntity.ok().build();
        } catch (Exception exception) {
            throw new ResponseStatusException(BAD_REQUEST, "Expired link");
        }
    }

}
