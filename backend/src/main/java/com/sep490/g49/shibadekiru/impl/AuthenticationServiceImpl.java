package com.sep490.g49.shibadekiru.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sep490.g49.shibadekiru.dto.*;
import com.sep490.g49.shibadekiru.entity.*;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.repository.RoleRepository;
import com.sep490.g49.shibadekiru.repository.TokenRepository;
import com.sep490.g49.shibadekiru.repository.UserAccountRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private StudentServiceImpl studentService;

    @Autowired
    private LecturesServiceImpl lecturesService;

    @Autowired
    private JWTServiceImpl jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public void register(RegisterResponse request) {
        Role role = roleRepository.findById(request.getRoleId()).orElseThrow(() -> new ResourceNotFoundException("Role "));
        var user = UserAccount.builder()
                .nickName(request.getNickName())
                .memberId(request.getMemberId())
                .userName(request.getUserName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .resetCode(null)
                .isBanned(false)
                .role(role)
                .build();

        Optional<UserAccount> existingUser = userAccountRepository.findByEmailOrMemberId(request.getEmail(), request.getMemberId());
        if (existingUser.isPresent()) {
            throw new IllegalStateException("Email already or member be exists.");
        } else {
            var savedUser = userAccountRepository.save(user);

            for (RoleType roleType : RoleType.values()) {
                if (roleType.getId() == role.getRoleId()) {
                    if (roleType == RoleType.STUDENT) {
                        Student student = new Student();
                        student.setFirstName(request.getFirstName());
                        student.setLastName(request.getLastName());
                        student.setEmail(request.getEmail());
                        student.setUserAccount(savedUser);
                        studentService.createStudentFromUserAccount(student);
                    } else if (roleType == RoleType.LECTURE) {
                        Lectures lectures = new Lectures();
                        lectures.setFirstName(request.getFirstName());
                        lectures.setLastName(request.getLastName());
                        lectures.setEmail(request.getEmail());
                        lectures.setUserAccount(savedUser);
                        lecturesService.createLecturerFromUserAccount(lectures);
                    }
                    break;
                }
            }


            var jwtToken = jwtService.generateToken( user);
            //var refreshToken = jwtService.generateRefreshToken(user);
            saveUserToken(savedUser, jwtToken);

        }




    }


    public AuthenticationDto authenticate(AuthenticationLoginDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userAccountRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
//        revokeAllUserRefreshTokens(user);
        saveUserToken(user, jwtToken);
//        saveUserRefreshToken(user, refreshToken);
        return AuthenticationDto.builder()
                .accessToken(jwtToken)
//                .refreshToken(refreshToken)
                .build();
    }


    private void saveUserToken(UserAccount user, String jwtToken) {
        var existingToken = tokenRepository.findByUserAccountAndExpiredAndRevoked(user, false, false);
        Token token;
        if (existingToken.isPresent()) {
            token = existingToken.get();
            token.setToken(jwtToken);
        } else {

            token = Token.builder()
                    .userAccount(user)
                    .token(jwtToken)
                    .tokenType(TokenType.BEARER)
                    .expired(false)
                    .revoked(false)
                    .build();
        }
        tokenRepository.save(token);
    }

    private void saveUserRefreshToken(UserAccount user, String jwtToken) {
        var existingToken = tokenRepository.findByUserAccountAndExpiredAndRevoked(user, true, true);
        Token token;
        if (existingToken.isPresent()) {
            token = existingToken.get();
            token.setToken(jwtToken);
        } else {

            token = Token.builder()
                    .userAccount(user)
                    .token(jwtToken)
                    .tokenType(TokenType.BEARER)
                    .expired(true)
                    .revoked(true)
                    .build();
        }
        tokenRepository.save(token);
    }


    private void revokeAllUserTokens(UserAccount user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getUserAccountId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(false);
            token.setRevoked(false);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public UserAccountDto getUserInfoByToken(String token) {
        UserAccount users = tokenRepository.findUserAccountByToken(token);
        System.out.println("usser" + users.getRole().getRoleId());
            UserAccount user = users;
            RoleType roleType = RoleType.valueOf(RoleType.getRoleTypeById(Math.toIntExact(user.getRole().getRoleId())));
            user.getRole().setRoleType(roleType);
            UserAccountDto userAccountDto = new UserAccountDto();
            userAccountDto.setUserAccountId(user.getUserAccountId());
            userAccountDto.setUserName(user.getUsername());
            userAccountDto.setEmail(user.getEmail());
            userAccountDto.setNickName(user.getNickName());
            userAccountDto.setMemberId(user.getMemberId());
            userAccountDto.setPassword(user.getPassword());
            userAccountDto.setIsBanned(user.getIsBanned());
            userAccountDto.setResetCode(user.getResetCode());
            userAccountDto.setRole(user.getRole());

            return userAccountDto;

    }



    private void revokeAllUserRefreshTokens(UserAccount user) {
        var validUserTokens = tokenRepository.findAllValidRefreshTokenByUser(user.getUserAccountId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.userAccountRepository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                revokeAllUserRefreshTokens(user);
                saveUserToken(user, accessToken);
                saveUserRefreshToken(user, accessToken);
                var authResponse = AuthenticationDto.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
