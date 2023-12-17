package com.sep490.g49.shibadekiru.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sep490.g49.shibadekiru.dto.*;
import com.sep490.g49.shibadekiru.entity.*;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.repository.*;
import com.sep490.g49.shibadekiru.util.JWTUtilityService;
import com.sep490.g49.shibadekiru.util.MailServiceProvider;
import com.sep490.g49.shibadekiru.util.RandomStringGeneratorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AuthenticationServiceImpl {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private LecturersRepository lecturersRepository;

    @Autowired
    private StudentRepository studentRepository;

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

    @Autowired
    private JWTUtilityService jwtUtilityService;

    @Autowired
    private MailServiceProvider mailServiceProvider;

    @Autowired
    RandomStringGeneratorService randomStringGeneratorService;

    public void register(RegisterResponse request) {

        Optional<UserAccount> existingUser = userAccountRepository.findByEmailOrMemberId(request.getEmail(), request.getMemberId());
        if (existingUser.isPresent()) {
            throw new IllegalStateException("Email already or member be exists.");
        } else {

            UserAccount userAccount = new UserAccount();

            userAccount.setNickName(request.getNickName());
            userAccount.setUserName(request.getNickName());

            String memberId;
            do {
                memberId = "USER" + randomStringGeneratorService.randomAlphaNumeric(5);
            } while (userAccountRepository.existsByMemberId(memberId));
            userAccount.setMemberId(memberId);

            String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

            Pattern pattern = Pattern.compile(emailPattern);
            Matcher matcher = pattern.matcher(request.getEmail());

            if (matcher.matches()) {
                System.out.println("Email hợp lệ");
                userAccount.setEmail(request.getEmail());

                userAccount.setPassword(passwordEncoder.encode(request.getPassword()));
                userAccount.setIsActive(false);
                userAccount.setIsBanned(false);
                userAccount.setIsCreatedByAdmin(false);

                Long roleId = request.getRoleId();

                Role role = roleRepository.findById(roleId).orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + roleId));

                userAccount.setRole(role);

                int minus = 10;

                String resetCode = jwtUtilityService.createJWT(userAccount, minus);
                System.out.println("Reset code trong register: " + resetCode);
                userAccount.setResetCode(resetCode);

                UserAccount savedUser = userAccountRepository.save(userAccount);

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

                //var jwtToken = jwtService.generateToken(userAccount);
                //var refreshToken = jwtService.generateRefreshToken(user);
                sendEmail(userAccount.getEmail(), resetCode, minus);
                //saveUserToken(savedUser, jwtToken);
            }

//            if (request.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
//
//            }
            else {
                throw new RuntimeException("Vui lòng dạng. Ex: abc@gmail.com");
            }


        }

    }

    private void sendEmail(String recipientEmail, String resetCode, int minus) {

        String resetLink = "http://localhost:4200/verify/" + resetCode;

        String subject = "Here's the link to activate your account";
        String content = "<p>Hello,</p>"
                + "<p>You have requested to activate your account.</p>"
                + "<p>Click the link below to activate your account:</p>"
                + "<p><a href='"+ resetLink +"'>Active Now</a></p>"
                + "<br>"
                + "<p> This link will expire <strong style=\"color: red; font-size: 18px;\"> "+minus+" </strong> minutes after the email is sent. </p>";
        mailServiceProvider.sendEmail(recipientEmail, subject, content);
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
        System.out.println("User" + users.getRole().getRoleId());
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
            userAccountDto.setIsActive(user.getIsActive());
            userAccountDto.setResetCode(user.getResetCode());
            userAccountDto.setIsCreatedByAdmin(user.getIsCreatedByAdmin());
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
