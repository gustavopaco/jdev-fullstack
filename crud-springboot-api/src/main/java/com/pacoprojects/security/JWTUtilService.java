package com.pacoprojects.security;

import com.pacoprojects.model.Constantes;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTUtilService {

    public Map<String, Object> generateJwt(String username, long JWT_EXPIRATION_TIME) {
        Map<String, Object> map = new HashMap<>();
        String JWT =
                /* Chama o gerador de Token */
                Jwts.builder()
                        /* HEADER - Definindo qual algoritmo sera utilizado para autenticacao E qual palavra chave utilizada para criptografar */
                        .signWith(SignatureAlgorithm.HS512, Constantes.SECRET.getValue())
                        /* PAYLOAD - Setando o tempo de expiracao do Token (Data e Hora atual + 2 dias)*/
                        .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_TIME))
                        /* PAYLOAD -  Adicionando o usuario(login) dentro do Token */
                        .setSubject(username)
                        /* Compactando Header + Payload + Signature */
                        .compact();

        /* Montando Token - Bearer Token*/
        String token = Constantes.TOKEN_PREFIX.getValue().concat(" ").concat(JWT);
        map.put("basicToken", JWT);
        map.put("token", token);
        return map;
    }


    public Map<String, Object> breakToken(HttpServletRequest request) {

        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        Map<String, Object> map = new HashMap<>();

        if (token == null) {return map;} /* Usuario nao autorizado*/

        /* Removendo o Bearer do token junto com o espaco em branco e retornado o token basico*/
        String basicToken = token.substring(Constantes.TOKEN_PREFIX.getValue().length()).strip();

        /* Faz a validacao do token do Usuario */
        Claims claims =
                /* Chama o descompactador de Token*/
                Jwts.parser()
                        /* Setando a chave secreta para quebra de criptografia do token*/
                        .setSigningKey(Constantes.SECRET.getValue())
                        /* Qubrando o Token Basico, sem o Bearer e o espaco em branco */
                        .parseClaimsJws(basicToken)
                        /* Obtendo o Corpo do JWT (Payload) */
                        .getBody();

        /* Obtendo o Usuario dono do token - username */
        String username = claims.getSubject();

        if (username == null) {return map;} /* Usuario nao autorizado*/

        map.put("basicToken", basicToken);
        map.put("username", username);
        return map;
    }
}
