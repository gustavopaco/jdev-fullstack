package br.com.curso.webmvnspringbootmicroservicos.security.JWTAlex;

import br.com.curso.webmvnspringbootmicroservicos.model.Usuario;
import br.com.curso.webmvnspringbootmicroservicos.repository.UsuarioRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class JWTTokenAutenticacaoService {

    private final UsuarioRepository usuarioRepository;

    // IMPORTANT: Tempo de validade do JWT - 2 Dias
    private static final long EXPIRATION_TIME = 172800000;
    // private static final long EXPIRATION_TIME = 300000;

    // IMPORTANT: Senha unica que eh adicionada com outros dados para a autenticacao do JWT
    private static final String SECRET = "SenhaExtremamenteSecreta";

    // IMPORTANT: Prefixo que compoe o JWT
    private static final String TOKEN_PREFIX = "Bearer";

    // IMPORTANT: Chave do mapa hash Map<Key,Value> para poder ser chamado e identificar o token
    private static final String HEADER_STRING = "Authorization";

    // IMPORTANT: Gerando Token de autenticacao e adicionando ao cabecalho de resposta Http
    public void addAuthentication(HttpServletRequest request, HttpServletResponse response, User usuario) throws IOException {

        Usuario usuarioConsultado = usuarioRepository.findUsuarioByLogin(usuario.getUsername());

        Map<String, Object> map = generateTokenUser(request, usuarioConsultado);
        String token = (String) map.get("token");
        String tokenFormatado = (String) map.get("tokenFormatado");

        // Gravando Token gerado no banco de dados para logo depois gerar resposta Http
        usuarioConsultado.setJwt(tokenFormatado);
        usuarioRepository.save(usuarioConsultado);

        // Adicionando Token ao cabecalho Http
        response.addHeader(HEADER_STRING, token);

        // Metodo que da permissao Ajax CORS, implementado abaixo, metodo principal na camada WebSecurity(*)
        // openCors(response);

        // Escrevendo token como resposta no Corpo Http
        response.getWriter().write("{\"Authorization\": \"" + token + "\"}");
    }

    /* IMPORTANT: Retorna o usuario validado com o token ou caso nao seja valido retorna null */
    public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response) {

        // Pega o token enviado no cabecalho Http
        String token = request.getHeader(HEADER_STRING);

        if (token != null) {

            String tokenFormatado = token.substring("Bearer ".length());

            // Descriptografa o token objeto o payload
            Claims claims = Jwts.parser()

                    // Passando o segredo definido para descriptografia
                    .setSigningKey(SECRET)

                    //  Removendo o Bearer do inicio do token e descriptografando
                    .parseClaimsJws(tokenFormatado)

                    //  Descriptografando o token e obtendo o Payload(Subject) das partes {Header, Payload, Signature}
                    .getBody();

            String username = claims.getSubject();
            List<?> list = (List<?>) claims.get("roles");
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            for (Object role : list) {
                authorities.add(new SimpleGrantedAuthority(role.toString()));
            }

            if (username != null) {

                Usuario usuarioConsultado = usuarioRepository.findUsuarioByLogin(username);

                if (tokenFormatado.equals(usuarioConsultado.getJwt())) {
                    return new UsernamePasswordAuthenticationToken(username, null, authorities);
                }
            }

            // IMPORTANT: Outro jeito de validar usuario realizando consulta ao banco apos validar o Token possuindo atributo username
            // if (username != null) {
            // Pesquisa o usuario no Banco de dados a partir do username
            // Usuario usuario = usuarioRepository.findUsuarioByLogin(username);
            //
            // if (usuario != null) {
            //  Retorna usuario com seus dados criptografados
            // return new UsernamePasswordAuthenticationToken(usuario.getUsername(), usuario.getPassword(), usuario.getAuthorities());
            // }
            // }
        }

        // Metodo que da permissao Ajax CORS, implementado abaixo, metodo principal na camada WebSecurity(*)
        // openCors(response);
        // Usuario nao autorizado
        return null;
    }

    // IMPORTANT: Metodo que da permisao para outras applicacoes  atraves de requisicoes AJAX
    //  melhor implementacao direto no WebSecurity(*), porem codigo de EXEMPLO aqui.
    @Deprecated
    private void openCors(HttpServletResponse response) {

        if (response.getHeader("Acess-Control-Allow-Origin") == null) {
            response.addHeader("Acess-Control-Allow-Origin", "*");
        }

        if (response.getHeaders("Access-Control-Allow-Headers") == null) {
            response.addHeader("Access-Control-Allow-Headers", "*");
        }

        if (response.getHeader("Access-Control-Request-Headers") == null) {
            response.addHeader("Access-Control-Request-Headers", "*");
        }

        if (response.getHeader("Access-Control-Allow-Methods") == null) {
            response.addHeader("Access-Control-Allow-Methods", "*");
        }
    }

    // IMPORTANT: Metodo generico de criacao de Token com criptografia HS512
    public Map<String, Object> generateTokenUser(HttpServletRequest request, Usuario usuario) {

        Map<String, Object> map = new HashMap<>();
        map.put("roles", usuario.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        map.put("ip", request.getRemoteAddr());

        // Montagem do token
        String JWT = Jwts.builder()

                // Adiciona o username para ser criptografado
                .setSubject(usuario.getUsername())

                // Definindo a data e hora e expiracao do token
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))

                // Setando URL de onde foi enviado o token
                .setIssuer(request.getRequestURL().toString())

                .addClaims(map)

                // Definindo o algoritmo e Senha unica usada para criptografar os dados
                .signWith(SignatureAlgorithm.HS512, SECRET)

                // Compactacao e algoritmo de geracao de senha
                .compact();

        // Juntando Prefixo + JWT
        String token = TOKEN_PREFIX + " " + JWT;

        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("token", token);
        objectMap.put("tokenFormatado", JWT);

        return objectMap;
    }
}
