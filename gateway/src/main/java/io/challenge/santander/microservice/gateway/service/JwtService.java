package io.challenge.santander.microservice.gateway.service;

import io.challenge.santander.microservice.gateway.config.PropertiesConfig;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    // 🔥 secreto simple (en producción usa RSA)
    private final String SECRET_KEY ;

    public JwtService(PropertiesConfig props) {
        SECRET_KEY=props.getToken();
    }

    // ⏱ 1 hora
    private final long EXPIRATION = 1000 * 60 * 60;

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "USER");
        claims.put("customClaim", "customValue");

        return Jwts.builder()
                .setSubject(username)
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(
                        Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                )
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
