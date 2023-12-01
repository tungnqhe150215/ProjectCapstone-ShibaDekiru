package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.dto.AuthenticationDto;
import com.sep490.g49.shibadekiru.dto.AuthenticationLoginDto;
import com.sep490.g49.shibadekiru.dto.RegisterResponse;
import com.sep490.g49.shibadekiru.entity.*;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.impl.AuthenticationServiceImpl;
import com.sep490.g49.shibadekiru.impl.JWTServiceImpl;
import com.sep490.g49.shibadekiru.impl.LecturesServiceImpl;
import com.sep490.g49.shibadekiru.impl.StudentServiceImpl;
import com.sep490.g49.shibadekiru.repository.*;

import com.sep490.g49.shibadekiru.util.JWTUtilityService;
import com.sep490.g49.shibadekiru.util.MailServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationServiceImplTest {

    @Mock
    private UserAccountRepository userAccountRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private StudentServiceImpl studentService;

    @Mock
    private LecturesServiceImpl lecturesService;

    @Mock
    private JWTServiceImpl jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JWTUtilityService jwtUtilityService;

    @Mock
    private MailServiceProvider mailServiceProvider;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register() {
        // Arrange
        RegisterResponse registerRequest = new RegisterResponse();
        registerRequest.setEmail("test@example.com");
        registerRequest.setRoleId(1L);
        Long RoleId = registerRequest.getRoleId();
        Role role = new Role();

        when(roleRepository.findById(RoleId)).thenReturn(Optional.of(role));
        when(userAccountRepository.findByEmailOrMemberId(anyString(), anyString())).thenReturn(Optional.empty());
        when(jwtUtilityService.createJWT(any(), anyInt())).thenReturn("resetCode");
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");

        // Act
        assertDoesNotThrow(() -> authenticationService.register(registerRequest));

        // Assert
        verify(roleRepository, times(1)).findById(anyLong());
        verify(userAccountRepository, times(1)).findByEmailOrMemberId(anyString(), anyString());
        verify(jwtUtilityService, times(1)).createJWT(any(), anyInt());
        verify(passwordEncoder, times(1)).encode(any());
        verify(userAccountRepository, times(1)).save(any());
        // Add more assertions as needed
    }

    @Test
    void authenticate() {
        // Arrange
        AuthenticationLoginDto loginRequest = new AuthenticationLoginDto();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password");

        UserAccount user = new UserAccount();
        user.setEmail("test@example.com");
        when(userAccountRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(jwtService.generateToken(any())).thenReturn("accessToken");
        when(jwtService.generateRefreshToken(any())).thenReturn("refreshToken");
        when(tokenRepository.findByUserAccountAndExpiredAndRevoked(any(), anyBoolean(), anyBoolean())).thenReturn(Optional.empty());
        when(tokenRepository.save(any())).thenReturn(new Token());

        // Act
        AuthenticationDto authenticationDto = authenticationService.authenticate(loginRequest);

        // Assert
        assertNotNull(authenticationDto);
        assertEquals("accessToken", authenticationDto.getAccessToken());
        // Add more assertions if needed
    }

    // Add more test cases for other methods as needed
}
