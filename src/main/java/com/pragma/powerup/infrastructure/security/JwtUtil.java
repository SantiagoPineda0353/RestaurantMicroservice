package com.pragma.powerup.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    private Key getSigningKey(){
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public Long extractUserId(String token){
        return extractAllClaims(token).get("id",Long.class);
    }

    public Long extractIdRole(String token){
        return extractAllClaims(token).get("idRole",Long.class);
    }

    public boolean isTokenValid(String token) {
        return extractAllClaimsSafely(token)
                .map(claims -> claims.getExpiration().after(new Date()))
                .orElse(false);
    }

    private Optional<Claims> extractAllClaimsSafely(String token) {
        try {
            return Optional.of(extractAllClaims(token));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}












