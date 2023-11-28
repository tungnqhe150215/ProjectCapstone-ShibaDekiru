package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.dto.ChangePasswordDto;
import com.sep490.g49.shibadekiru.dto.LecturesDto;
import com.sep490.g49.shibadekiru.dto.StudentDto;
import com.sep490.g49.shibadekiru.dto.UserAccountDto;
import com.sep490.g49.shibadekiru.entity.*;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.impl.JWTServiceImpl;
import com.sep490.g49.shibadekiru.impl.LecturesServiceImpl;
import com.sep490.g49.shibadekiru.impl.StudentServiceImpl;
import com.sep490.g49.shibadekiru.impl.UserAccountServiceImpl;
import com.sep490.g49.shibadekiru.repository.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Principal;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserAccountServiceImplTest {

    @Mock
    private UserAccountRepository userAccountRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private LecturersRepository lecturersRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JWTServiceImpl jwtService;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private StudentServiceImpl studentService;

    @Mock
    private LecturesServiceImpl lecturesService;

    @InjectMocks
    private UserAccountServiceImpl userAccountService;

    @Test
    void getAllUserAccounts() {
        // Mock data
        when(userAccountRepository.findAll()).thenReturn(Collections.emptyList());

        // Test
        var result = userAccountService.getAllUserAccounts();

        // Assertions
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void createUserAccount() {
        // Mock data
        UserAccount userAccountRequest = new UserAccount();
        Role role = new Role();
        role.setRoleId(1L);
        userAccountRequest.setRole(role);

        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(userAccountRepository.save(any(UserAccount.class))).thenReturn(userAccountRequest);

        // Test
        var createdUserAccount = userAccountService.createUserAccount(userAccountRequest);

        // Assertions
        assertNotNull(createdUserAccount);
        assertEquals(userAccountRequest, createdUserAccount);
    }

    @Test
    void updateUserAccount() {
        // Mock data
        Long userAccountId = 1L;
        UserAccount userAccountRequest = new UserAccount();
        Role role = new Role();
        role.setRoleId(1L);
        userAccountRequest.setRole(role);

        UserAccount existingUserAccount = new UserAccount();
        existingUserAccount.setUserAccountId(userAccountId);

        when(userAccountRepository.findById(userAccountId)).thenReturn(Optional.of(existingUserAccount));
        when(userAccountRepository.save(any(UserAccount.class))).thenReturn(userAccountRequest);

        // Test
        var updatedUserAccount = userAccountService.updateUserAccount(userAccountId, userAccountRequest);

        // Assertions
        assertNotNull(updatedUserAccount);
        assertEquals(userAccountId, updatedUserAccount.getUserAccountId());
        assertEquals(userAccountRequest, updatedUserAccount);
    }

    @Test
    void updateIsBanned() {
        // Mock data
        Long userAccountId = 1L;
        UserAccount existingUserAccount = new UserAccount();
        existingUserAccount.setUserAccountId(userAccountId);
        existingUserAccount.setIsBanned(false);

        when(userAccountRepository.findById(userAccountId)).thenReturn(Optional.of(existingUserAccount));
        when(userAccountRepository.save(any(UserAccount.class))).thenReturn(existingUserAccount);

        // Test
        userAccountService.updateIsBanned(userAccountId);

        // Assertions
        assertTrue(existingUserAccount.getIsBanned());
    }

    @Test
    void getUserAccountById() {
        // Mock data
        Long userAccountId = 1L;
        UserAccount userAccount = new UserAccount();
        when(userAccountRepository.findById(userAccountId)).thenReturn(Optional.of(userAccount));

        // Test
        var result = userAccountService.getUserAccountById(userAccountId);

        // Assertions
        assertNotNull(result);
        assertEquals(userAccount, result);
    }

    @Test
    void getUserAccountByEmail() {
        // Mock data
        String email = "test@example.com";
        UserAccount userAccount = new UserAccount();
        when(userAccountRepository.findByEmail(email)).thenReturn(Optional.of(userAccount));

        // Test
        var result = userAccountService.getUserAccountByEmail(email);

        // Assertions
        assertNotNull(result);
        assertEquals(userAccount, result);
    }

    @Test
    void changePassword() {
        // Mock data
        ChangePasswordDto request = new ChangePasswordDto();
        request.setCurrentPassword("currentPassword");
        request.setNewPassword("newPassword");
        request.setConfirmationPassword("newPassword");

        Principal connectedUser = mock(Principal.class);
        UsernamePasswordAuthenticationToken authenticationToken = mock(UsernamePasswordAuthenticationToken.class);
        UserAccount user = new UserAccount();
        user.setPassword(passwordEncoder.encode("currentPassword"));
        when(authenticationToken.getPrincipal()).thenReturn(user);
        when(connectedUser instanceof UsernamePasswordAuthenticationToken).thenReturn(true);
        when(connectedUser).thenReturn(authenticationToken);
        when(passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())).thenReturn(true);

        // Test
        assertDoesNotThrow(() -> userAccountService.changePassword(request, connectedUser));
    }

    @Test
    void updateProfile() {
        // Mock data
        UserAccountDto request = new UserAccountDto();
        Principal connectedUser = mock(Principal.class);
        UsernamePasswordAuthenticationToken authenticationToken = mock(UsernamePasswordAuthenticationToken.class);
        UserAccount user = new UserAccount();
        when(authenticationToken.getPrincipal()).thenReturn(user);
        when(connectedUser instanceof UsernamePasswordAuthenticationToken).thenReturn(true);
        when(connectedUser).thenReturn(authenticationToken);

        when(userAccountRepository.save(user)).thenReturn(user);

        // Test
        var result = userAccountService.updateProfile(request, connectedUser);

        // Assertions
        assertNotNull(result);
        assertEquals(user, result);
    }

    @Test
    void updateProfileStudent() {
        // Mock data
        StudentDto request = new StudentDto();
        Principal connectedUser = mock(Principal.class);
        UsernamePasswordAuthenticationToken authenticationToken = mock(UsernamePasswordAuthenticationToken.class);
        UserAccount user = new UserAccount();
        when(authenticationToken.getPrincipal()).thenReturn(user);
        when(connectedUser instanceof UsernamePasswordAuthenticationToken).thenReturn(true);
        when(connectedUser).thenReturn(authenticationToken);

        when(userAccountRepository.findByMemberId(user.getMemberId())).thenReturn(user);
        when(studentService.getByUserAccount(user)).thenReturn(new Student());
        when(studentRepository.save(any(Student.class))).thenReturn(new Student());

        // Test
        assertDoesNotThrow(() -> userAccountService.updateProfileStudent(request, connectedUser));
    }

    @Test
    void updateProfileLecture() {
        // Mock data
        LecturesDto request = new LecturesDto();
        Principal connectedUser = mock(Principal.class);
        UsernamePasswordAuthenticationToken authenticationToken = mock(UsernamePasswordAuthenticationToken.class);
        UserAccount user = new UserAccount();
        when(authenticationToken.getPrincipal()).thenReturn(user);
        when(connectedUser instanceof UsernamePasswordAuthenticationToken).thenReturn(true);
        when(connectedUser).thenReturn(authenticationToken);

        when(userAccountRepository.findByMemberId(user.getMemberId())).thenReturn(user);
        when(lecturesService.getByUserAccount(user)).thenReturn(new Lectures());
        when(lecturersRepository.save(any(Lectures.class))).thenReturn(new Lectures());

        // Test
        assertDoesNotThrow(() -> userAccountService.updateProfileLecture(request, connectedUser));
    }

    @Test
    void updateResetCode() {
        // Mock data
        String resetCode = "123456";
        String email = "test@example.com";
        when(userAccountRepository.findByEmail(email)).thenReturn(Optional.of(new UserAccount()));

        // Test
        assertDoesNotThrow(() -> userAccountService.updateResetCode(resetCode, email));
    }

    @Test
    void getByResetCode() {
        // Mock data
        String resetCode = "123456";
        when(userAccountRepository.findByResetCode(resetCode)).thenReturn(new UserAccount());

        // Test
        var result = userAccountService.getByResetCode(resetCode);

        // Assertions
        assertNotNull(result);
    }

    @Test
    void updatePassword() {
        // Mock data
        UserAccount userAccount = new UserAccount();
        String newPassword = "newPassword";
        when(passwordEncoder.encode(newPassword)).thenReturn("encodedPassword");

        // Test
        assertDoesNotThrow(() -> userAccountService.updatePassword(userAccount, newPassword));
    }
}
