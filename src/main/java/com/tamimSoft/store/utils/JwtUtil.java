package com.tamimSoft.store.utils;

import com.tamimSoft.store.exception.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "sfjpaoisfn+ijisfasdfsoj+rsfisjfkmkofjpiehif";

    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 2; // 2 hours

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().claims(claims).subject(subject).header().empty().add("type", "JWT").and().issuedAt(new Date(System.currentTimeMillis())).expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 10 hours
                .signWith(getSigningKey()).compact();
    }

    public String extractUsername(String jwtToken) {
        return extractClaim(jwtToken).getSubject();
    }

    private Claims extractClaim(String jwtToken) {
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(jwtToken).getPayload();
    }

    public boolean validateToken(String jwtToken, UserDetails userDetails) {
        String username = extractUsername(jwtToken);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken));
    }

    private boolean isTokenExpired(String jwtToken) {
        return extractClaim(jwtToken).getExpiration().before(new Date());
    }

    public boolean isTokenValid(String jwtToken) {
        try {
            extractClaim(jwtToken);
            return true;
        } catch (Exception e) {
            throw new InvalidTokenException("Invalid token");
        }
    }
}
