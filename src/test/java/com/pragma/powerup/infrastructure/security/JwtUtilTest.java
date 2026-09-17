package com.pragma.powerup.infrastructure.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private static final String SECRET = "7f3a9c1e5b8d2f6a4c9e1b7d3f5a8c2e6b9d4f1a7c3e5b8d2f6a9c1e4b7d3f5a";
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        ReflectionTestUtils.setField(jwtUtil, "secret", SECRET);
    }

    private String buildToken(long expirationOffsetMillis) {
        return io.jsonwebtoken.Jwts.builder()
                .setSubject("propietario@correo.com")
                .claim("id", 5L)
                .claim("idRole", 2L)
                .setIssuedAt(new java.util.Date())
                .setExpiration(new java.util.Date(System.currentTimeMillis() + expirationOffsetMillis))
                .signWith(io.jsonwebtoken.security.Keys.hmacShaKeyFor(SECRET.getBytes()))
                .compact();
    }

    @Test
    void extractUserId_thenReturnCorrectId() {
        String token = buildToken(60000);
        assertEquals(5L, jwtUtil.extractUserId(token));
    }

    @Test
    void extractIdRole_thenReturnCorrectIdRole() {
        String token = buildToken(60000);
        assertEquals(2L, jwtUtil.extractIdRole(token));
    }

    @Test
    void isTokenValid_whenInvalidToken_thenValid() {
        String token = buildToken(60000);
        assertTrue(jwtUtil.isTokenValid(token));
    }

    @Test
    void isTokenValid_whenTokenExpired_thenInvalid() {
        String token = buildToken(-1000);
        assertFalse(jwtUtil.isTokenValid(token));
    }
}