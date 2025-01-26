package com.casualchess.core.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtCore {
    // TODO this should be a secret
    private final SecretKey key = Keys.hmacShaKeyFor("casual-chess-core-key-that-must-be-longer".getBytes());

    public String generateToken(String subject) {
        return Jwts.builder()
            .subject(subject)
            .signWith(key)
            .compact();
    }

    /**
     * Returns the subject from the token
     *
     * @param token JWT token
     * @return the subject from the token
     * @throws JwtException             if the {@code token} string cannot be parsed or validated as required.
     * @throws IllegalArgumentException if the {@code token} string is {@code null} or empty or only whitespace
     */
    public String extractToken(String token) {
        return extractClaims(token).getSubject();
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }
}
