package com.pacoprojects.security.jwt;

import com.google.common.base.Strings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.Period;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtUtilService {

    private final JwtConfig config;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(config.getSecretKey().getBytes());
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(userDetails, new HashMap<>());
    }

    public String generateToken(UserDetails userDetails, Map<String, Object> objectMap) {
        return Jwts
                .builder()
                .setClaims(objectMap)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(Period.ofDays(config.getTokenExpirationAfterDays()))))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Map<String, Object> breakToken(HttpServletRequest request) {
        Map<String, Object> objectMap = new HashMap<>();
        String fullToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (!Strings.isNullOrEmpty(fullToken)) {
            String basicToken = fullToken.replace(config.getPrefixToken(),"");

            if (!isTokenExpired(basicToken)) {
                String username = extractUsername(basicToken);
                objectMap.put("username", username);
                objectMap.put("basicToken", basicToken);
                return objectMap;
            }
        }
        return objectMap;
    }

    private Claims extractAllClaims(String basicToken) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(basicToken)
                .getBody();
    }

    private <T> T extractClaim(String basicToken, Function<Claims, T> claimsTFunction) {
        final Claims claims = extractAllClaims(basicToken);
        return claimsTFunction.apply(claims);
    }

    public String extractUsername(String basicToken) {
        return extractClaim(basicToken, Claims::getSubject);
    }

    private Date extractExpirationDate(String basicToken) {
        return extractClaim(basicToken, Claims::getExpiration);
    }

    public boolean isTokenExpired(String basicToken) {
        Date dateExpiration = extractExpirationDate(basicToken);
        return new Date().after(dateExpiration);
    }
}
