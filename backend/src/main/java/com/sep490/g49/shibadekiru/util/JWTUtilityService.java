package com.sep490.g49.shibadekiru.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sep490.g49.shibadekiru.entity.UserAccount;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.impl.UserAccountServiceImpl;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Component
public class JWTUtilityService {

    @Autowired
    private UserAccountServiceImpl userAccountService;

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    public String createJWT(UserAccount userAccount, int minus) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        String token = JWT.create()
                .withIssuer("auth0")
                .withSubject("user")
                .withClaim("userAccountId", userAccount.getUserAccountId())
                .withClaim("nickname", userAccount.getNickName())
                .withClaim("email", userAccount.getEmail())
                .withJWTId(UUID.randomUUID().toString())
                .withIssuedAt(Date.from(Instant.now()))
                .withExpiresAt(Date.from(Instant.now().plus(minus, ChronoUnit.MINUTES)))
                .sign(algorithm);
        return token;
    }

    public UserAccount verifyToken(String token) throws Exception {
        try {
         Algorithm algorithm = Algorithm.HMAC256(secretKey);

            JWTVerifier jwtVerifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build();

            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            Date expiredDate = decodedJWT.getExpiresAt();

            if (expiredDate.getTime() < System.currentTimeMillis()) {
                throw new ResourceNotFoundException("Token expires!");
            }

            String userAccountId = decodedJWT.getClaims().get("userAccountId").toString();
            System.out.println("User Account Id: " + userAccountId);
            if (userAccountId != null) {

                Long userId = Long.parseLong(userAccountId);
                System.out.println("User Id:" + userId);
                UserAccount userAccount = userAccountService.getUserAccountById(userId);

                if (userAccount != null) {
                    return userAccount;
                }
            } else {
                throw new ResourceNotFoundException("Wrong token!");
            }
        } catch (JWTVerificationException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
        return null;
    }

    public boolean isTokenExpired(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            JWTVerifier jwtVerifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build();

            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            Date expiredDate = decodedJWT.getExpiresAt();

            return expiredDate.getTime() < System.currentTimeMillis();
        } catch (JWTVerificationException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @Getter
    private static final JWTUtilityService instance = new JWTUtilityService();

    private JWTUtilityService() {
    }

}
