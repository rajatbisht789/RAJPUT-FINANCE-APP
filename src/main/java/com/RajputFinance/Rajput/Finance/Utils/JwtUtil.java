package com.RajputFinance.Rajput.Finance.Utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    private final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SECRET_KEY) // Use the new signing method
                .compact();
    }

    public String extractPhoneNumber(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean isTokenExpired(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }

    public String generateToken(String phoneNumber) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, phoneNumber);
    }

    // Validate token
    public Boolean validateToken(String token, String phoneNumber) {
        final String tokenUsername = extractPhoneNumber(token);
        return (tokenUsername.equals(phoneNumber) && !isTokenExpired(token));
    }

    // Extract expiration date from token
    private Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    // Extract all claims from token
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }
}

