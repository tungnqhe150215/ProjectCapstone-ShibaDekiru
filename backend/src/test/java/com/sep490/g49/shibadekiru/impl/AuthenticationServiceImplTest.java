package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.dto.*;
import com.sep490.g49.shibadekiru.entity.*;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
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
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationServiceImplTest {

    @Mock
    private UserAccountRepository userAccountRepository;

    @Mock
    private LecturersRepository lecturersRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleRepository roleRepository;

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
    void register_shouldThrowException_whenEmailOrMemberIdExists() {
        // Arrange
        RegisterResponse request = new RegisterResponse();
        request.setEmail("test@gmail.com");
        request.setMemberId("123456");
        request.setRoleId(1L);

        when(userAccountRepository.findByEmailOrMemberId(request.getEmail(), request.getMemberId())).thenReturn(Optional.of(new UserAccount()));

        // Act and Assert
        assertThrows(IllegalStateException.class, () -> authenticationService.register(request));

        verify(userAccountRepository, times(1)).findByEmailOrMemberId(request.getEmail(), request.getMemberId());
        verifyNoInteractions(roleRepository, userAccountRepository);
    }

    @Test
    void register_shouldCreateUserAccountAndStudent_whenRoleIsStudent() {
        // Arrange
        RegisterResponse request = new RegisterResponse();
        request.setEmail("test@gmail.com");
        request.setMemberId("123456");
        request.setUserName("test");
        request.setPassword("password");
        request.setRoleId(1L);
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setNickName("JD");

        when(userAccountRepository.findByEmailOrMemberId(request.getEmail(), request.getMemberId())).thenReturn(Optional.empty());
        when(roleRepository.findById(request.getRoleId())).thenReturn(Optional.of(new Role()));
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        when(userAccountRepository.save(any(UserAccount.class))).thenReturn(new UserAccount());

        // Act
        authenticationService.register(request);

        // Assert
        verify(userAccountRepository, times(1)).findByEmailOrMemberId(request.getEmail(), request.getMemberId());
        verify(roleRepository, times(1)).findById(request.getRoleId());
        verify(passwordEncoder, times(1)).encode(request.getPassword());
        verify(userAccountRepository, times(1)).save(any(UserAccount.class));
        verify(studentService, times(1)).createStudentFromUserAccount(any(Student.class));
        verifyNoMoreInteractions(userAccountRepository, roleRepository, passwordEncoder, studentService);
    }

    @Test
    void register_shouldCreateUserAccountAndLecturer_whenRoleIsLecturer() {
        // Arrange
        RegisterResponse request = new RegisterResponse();
        request.setEmail("test@gmail.com");
        request.setMemberId("123456");
        request.setUserName("test");
        request.setPassword("password");
        request.setRoleId(2L);
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setNickName("JD");

        when(userAccountRepository.findByEmailOrMemberId(request.getEmail(), request.getMemberId())).thenReturn(Optional.empty());
        when(roleRepository.findById(request.getRoleId())).thenReturn(Optional.of(new Role()));
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        when(userAccountRepository.save(any(UserAccount.class))).thenReturn(new UserAccount());

        // Act
        authenticationService.register(request);

        // Assert
        verify(userAccountRepository, times(1)).findByEmailOrMemberId(request.getEmail(), request.getMemberId());
        verify(roleRepository, times(1)).findById(request.getRoleId());
        verify(passwordEncoder, times(1)).encode(request.getPassword());
        verify(userAccountRepository, times(1)).save(any(UserAccount.class));
        verify(lecturesService, times(1)).createLecturerFromUserAccount(any(LecturesDto.class));
        verifyNoMoreInteractions(userAccountRepository, roleRepository, passwordEncoder, lecturesService);
    }

    @Test
    void authenticate() {
        // Arrange
        AuthenticationLoginDto request = new AuthenticationLoginDto();
        request.setEmail("test@gmail.com");
        request.setPassword("password");

        UserAccount userAccount = new UserAccount();
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(userAccountRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(userAccount));
        when(jwtService.generateToken(userAccount)).thenReturn("accessToken");
        when(jwtService.generateRefreshToken(userAccount)).thenReturn("refreshToken");

        // Act
        AuthenticationDto result = authenticationService.authenticate(request);

        // Assert
        assertNotNull(result);
        assertEquals("accessToken", result.getAccessToken());
        assertEquals("refreshToken", result.getRefreshToken());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userAccountRepository, times(1)).findByEmail(request.getEmail());
        verify(jwtService, times(1)).generateToken(userAccount);
        verify(jwtService, times(1)).generateRefreshToken(userAccount);
        verifyNoMoreInteractions(authenticationManager, userAccountRepository, jwtService);
    }

    @Test
    void getUserInfoByToken_shouldReturnUserAccountDto_whenTokenIsValid() {
        // Arrange
        String token = "validToken";
        UserAccount userAccount = new UserAccount();
        userAccount.setEmail("test@gmail.com");
        userAccount.setRole(new Role());

        when(tokenRepository.findUserAccountByToken(token)).thenReturn(userAccount);

        // Act
        UserAccountDto result = authenticationService.getUserInfoByToken(token);

        // Assert
        assertNotNull(result);
        assertEquals(userAccount.getUserAccountId(), result.getUserAccountId());
        assertEquals(userAccount.getUsername(), result.getUserName());
        assertEquals(userAccount.getEmail(), result.getEmail());
        assertEquals(userAccount.getNickName(), result.getNickName());
        assertEquals(userAccount.getMemberId(), result.getMemberId());
        assertEquals(userAccount.getPassword(), result.getPassword());
        assertEquals(userAccount.getIsBanned(), result.getIsBanned());
        assertEquals(userAccount.getIsActive(), result.getIsActive());
        assertEquals(userAccount.getResetCode(), result.getResetCode());
        assertEquals(userAccount.getRole(), result.getRole());
        verify(tokenRepository, times(1)).findUserAccountByToken(token);
        verifyNoMoreInteractions(tokenRepository);
    }

    @Test
    void refreshToken() {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        String authHeader = "Bearer refreshToken";
        String accessToken = "newAccessToken";
        String userEmail = "test@gmail.com";

        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(authHeader);
        when(jwtService.extractUsername("refreshToken")).thenReturn(userEmail);
        when(userAccountRepository.findByEmail(userEmail)).thenReturn(Optional.of(new UserAccount()));
        when(jwtService.isTokenValid("refreshToken", new UserAccount())).thenReturn(true);
        when(jwtService.generateToken(new UserAccount())).thenReturn(accessToken);
        when(jwtService.generateRefreshToken(new UserAccount())).thenReturn("newRefreshToken");

        // Act
        //authenticationService.refreshToken(request, response);

        // Assert
        verify(request, times(1)).getHeader(HttpHeaders.AUTHORIZATION);
        verify(jwtService, times(1)).extractUsername("refreshToken");
        verify(userAccountRepository, times(1)).findByEmail(userEmail);
        verify(jwtService, times(1)).isTokenValid("refreshToken", new UserAccount());
        verify(jwtService, times(1)).generateToken(new UserAccount());
        verify(jwtService, times(1)).generateRefreshToken(new UserAccount());
        verifyNoMoreInteractions(request, jwtService, userAccountRepository);
    }
}