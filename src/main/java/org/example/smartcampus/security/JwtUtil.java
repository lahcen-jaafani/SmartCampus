package org.example.smartcampus.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private final Key key = Keys.hmacShaKeyFor("smartcampus-secret-key-256bits-long!!".getBytes());
    private final long expiration = 86400000;

    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parser().verifyWith((javax.crypto.SecretKey) key).build()
                .parseSignedClaims(token).getPayload().getSubject();
    }

    public boolean isValid(String token) {
        try { Jwts.parser().verifyWith((javax.crypto.SecretKey) key).build().parseSignedClaims(token); return true; }
        catch (JwtException e) { return false; }
    }
}
