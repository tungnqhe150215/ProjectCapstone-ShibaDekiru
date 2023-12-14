package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;

class LogoutServiceImplTest {

    @Mock
    private TokenRepository tokenRepository;

    @InjectMocks
    private LogoutServiceImpl logoutService;

//    @Test
//    void logout() {
//        // Mocking HttpServletRequest, HttpServletResponse, and Authentication
//        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
//        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
//        Authentication authentication = Mockito.mock(Authentication.class);
//
//        // Mocking the authHeader and jwt
//        Mockito.when(request.getHeader("Authorization")).thenReturn("Bearer mockJWT");
//        Mockito.when(authentication.getName()).thenReturn("mockUser");
//
//        // Mocking the findByToken method in TokenRepository
//        Mockito.when(tokenRepository.findByToken("mockJWT")).thenReturn(null); // Change this based on your actual logic
//
//        // Mocking SecurityContextHolder behavior
//        Mockito.doNothing().when(SecurityContextHolder.class);
//        SecurityContextHolder.clearContext();
//
//        // Performing the logout
//        logoutService.logout(request, response, authentication);
//
//        // Add assertions as needed based on your actual logic
//    }
}
