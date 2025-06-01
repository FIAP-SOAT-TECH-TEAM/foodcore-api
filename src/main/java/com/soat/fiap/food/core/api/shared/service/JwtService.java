package com.soat.fiap.food.core.api.shared.service;

import com.soat.fiap.food.core.api.shared.exception.BusinessException;
import com.soat.fiap.food.core.api.shared.exception.JwtException;
import com.soat.fiap.food.core.api.user.domain.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
public class JwtService {

    @Value("${jwt.secret_key}")
    private String secret;

    @Value("${jwt.expiration}")
    private Integer expiration;

    private SecretKey key;

    @PostConstruct
    public void init() {
        if (secret == null || secret.isBlank()) {
            throw new IllegalStateException("Chave JWT não configurada ou vazia!");
        }

        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);


        if (keyBytes.length < 64) {
            throw new IllegalArgumentException("A chave JWT precisa ter pelo menos 64 bytes para HS512.");
        }

        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("id", user.getId())
                .claim("username", user.getUsername())
                .claim("email", user.getEmail())
                .claim("name", user.getName())
                .claim("role", user.getRole().getName())
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plus(expiration, ChronoUnit.MINUTES)))
                .signWith(this.key, SignatureAlgorithm.HS512)
                .compact();
    }

    public void validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(this.key)
                    .build()
                    .parseClaimsJws(token);
        } catch (Exception e) {
            throw new JwtException("Token JWT inválido ou expirado.", e);
        }
    }

    public Long extractUserId(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(this.key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("id", Long.class);
    }

    public String extractUsername(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(this.key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public List<GrantedAuthority> extractRoles(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(this.key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        String role = claims.get("role", String.class);

        return List.of(new SimpleGrantedAuthority(String.format("ROLE_%s", role.toUpperCase())));
    }

}
