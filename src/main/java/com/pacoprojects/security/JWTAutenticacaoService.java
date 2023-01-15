package com.pacoprojects.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pacoprojects.model.Usuario;
import com.pacoprojects.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class JWTAutenticacaoService {

    private final JWTUtilService jwtUtilService;
    private final UsuarioRepository usuarioRepository;

    /* Tempo de validade do token - 2 Dias */
    private static final long JWTEXPIRATION_TIME = 172800000;

    /* Metodo que: Gera Token de Autenticacao | Salva Token gerado no Banco | Adiciona Token gerado no Header da resposta para Usuario*/
    public void setAuthentication(HttpServletResponse response, String username) throws Exception {

        Map<String, Object> mapToken = jwtUtilService.generateJwt(username, JWTEXPIRATION_TIME);

        if (mapToken.isEmpty()) {

            throw new Exception("Token nao foi gerado corretamente");
        } else {

            String token = mapToken.get("token").toString();

            /* Adiciona o Token no Header */
            response.addHeader(HttpHeaders.AUTHORIZATION, token);

            /* ** Comentar - Adicionar o Token no Body como JSON Exemplo** */
            String tokenBodyJSON = "{".concat(HttpHeaders.AUTHORIZATION.concat(": ".concat(token))).concat("}");
            new ObjectMapper().writeValue(response.getOutputStream(), tokenBodyJSON);
        }
    }

    /* Metodo que: Quebra o Token | Busca Usuario no Banco | Retorna Usuario e suas Permissoes OU USUARIO NULL*/
    public Authentication getAuthentication(HttpServletRequest request) throws Exception{
        Map<String, Object> map = jwtUtilService.breakToken(request);

        if (!map.isEmpty()) {
            String username = map.get("username").toString();
            String basicToken = map.get("basicToken").toString();

            Optional<Usuario> usuarioOptional = usuarioRepository.findUsuarioByLogin(username);

            if (usuarioOptional.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario nao foi encontrado ou se encontra bloqueado");
            }

            return new UsernamePasswordAuthenticationToken(usuarioOptional.get().getUsername(),null,usuarioOptional.get().getAuthorities());
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuario nao tem autorizacao para continuar"); /* Usuario nao autorizado */
    }
}
