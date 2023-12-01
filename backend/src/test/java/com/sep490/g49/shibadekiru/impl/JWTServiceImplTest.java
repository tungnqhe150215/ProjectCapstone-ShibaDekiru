package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.impl.JWTServiceImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JWTServiceImplTest {

    @InjectMocks
    private JWTServiceImpl jwtService;

    private static final String SECRET_KEY = "your-secret-key";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void extractUsername() {
        String token = createToken("testUser");
        String username = jwtService.extractUsername(token);
        assertEquals("testUser", username);
    }

    @Test
    void extractClaim() {
        String token = createToken("testUser");
        String subject = jwtService.extractClaim(token, Claims::getSubject);
        assertEquals("testUser", subject);
    }

    @Test
    void generateToken() {
        UserDetails userDetails = new User("testUser", "password", Collections.emptyList());
        String token = jwtService.generateToken(userDetails);
        assertNotNull(token);
    }

    @Test
    void testGenerateToken() {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("key1", "value1");
        UserDetails userDetails = new User("testUser", "password", Collections.emptyList());
        String token = jwtService.generateToken(extraClaims, userDetails);
        assertNotNull(token);
    }

    @Test
    void generateRefreshToken() {
        UserDetails userDetails = new User("testUser", "password", Collections.emptyList());
        String refreshToken = jwtService.generateRefreshToken(userDetails);
        assertNotNull(refreshToken);
    }

    @Test
    void isTokenValid() {
        String token = createToken("testUser");
        UserDetails userDetails = new User("testUser", "password", Collections.emptyList());
        assertTrue(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    void isTokenValid_ExpiredToken() {
        String expiredToken = createExpiredToken("testUser");
        UserDetails userDetails = new User("testUser", "password", Collections.emptyList());
        assertFalse(jwtService.isTokenValid(expiredToken, userDetails));
    }

    @Test
    void isTokenValid_InvalidToken() {
        String invalidToken = "invalidToken";
        UserDetails userDetails = new User("testUser", "password", Collections.emptyList());
        assertFalse(jwtService.isTokenValid(invalidToken, userDetails));
    }

    private String createToken(String username) {
        KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256);
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(privateKey)
                .compact();
    }

    private String createExpiredToken(String username) {
        KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256);
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() - 3600000)) // Expired token
                .signWith(privateKey)
                .compact();
    }
}
