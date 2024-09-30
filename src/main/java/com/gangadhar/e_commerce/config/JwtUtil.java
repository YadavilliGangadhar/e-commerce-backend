package com.gangadhar.e_commerce.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import static io.jsonwebtoken.Jwts.builder;


@Component
public class JwtUtil {

    private final SecretKey secretKey = Keys.hmacShaKeyFor("465ad1477ec5a3be727b7370f4351cba827ab099c124a19285609dc87b488d90".getBytes()); // Replace with your actual secret key
    private final long EXPIRATION_TIME = 1000 * 60 * 60; // Token expiration time (1 hour)

    // Generate JWT token
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    // Create a token with claims and subject
    private String createToken(Map<String, Object> claims, String subject) {
        return builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis())) // Issued time
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Expiration time
                .signWith(SignatureAlgorithm.HS256, secretKey) // Signing the token
                .compact();
    }

    // Extract username from the token
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Method to extract claims from the token
    private Claims extractAllClaims(String token) {
        // Use the Jwts.parser() method
        return Jwts.parser  () // Use parserBuilder()
                .setSigningKey(secretKey) // Set the signing key
                .build()
                .parseClaimsJws(token) // Call parseClaimsJws
                .getBody();
    }

    // Validate the token with the user details
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Check if the token has expired
    private Boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
}
