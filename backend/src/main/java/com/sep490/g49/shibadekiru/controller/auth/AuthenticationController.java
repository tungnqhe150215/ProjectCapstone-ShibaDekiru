package com.sep490.g49.shibadekiru.controller.auth;

import com.sep490.g49.shibadekiru.dto.*;
import com.sep490.g49.shibadekiru.entity.Lectures;
import com.sep490.g49.shibadekiru.entity.UserAccount;
import com.sep490.g49.shibadekiru.impl.*;
import com.sep490.g49.shibadekiru.repository.UserAccountRepository;
import com.sep490.g49.shibadekiru.service.ILecturesService;
import com.sep490.g49.shibadekiru.service.IStudentService;
import com.sep490.g49.shibadekiru.service.IUserAccountService;
import com.sep490.g49.shibadekiru.util.JWTUtilityService;
import com.sep490.g49.shibadekiru.util.MailServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
    private AuthenticationServiceImpl authenticationService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ILecturesService iLecturesService;

    @Autowired
    private IStudentService iStudentService;

    @Autowired
    private UserAccountServiceImpl userAccountService;

    @Autowired
    private JWTUtilityService jwtUtilityService;

    @Autowired
    private MailServiceProvider mailServiceProvider;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterResponse request
    ) {
        try {
            request.setRoleId((3L));
            authenticationService.register(request);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationDto> authenticate(
            @RequestBody AuthenticationLoginDto request
    ) {
        //return ResponseEntity.ok(authenticationService.authenticate(request));

        AuthenticationDto authResult = authenticationService.authenticate(request);

        String accessToken = authResult.getAccessToken();
        UserAccountDto userAccountDto = authenticationService.getUserInfoByToken(accessToken);

        if (userAccountDto.getIsBanned().equals(false)) {

            System.out.println("Token: " + accessToken);
            System.out.println("Nick name: " +  userAccountDto.getNickName());
            System.out.println("Member id: " +  userAccountDto.getMemberId());
            System.out.println("Pass word:" + userAccountDto.getPassword());
            System.out.println("Role id: " +  userAccountDto.getRole());
            System.out.println("US id: " +  userAccountDto.getUserName());

            UserAccount userAccount = modelMapper.map(userAccountDto,UserAccount.class);
            if ( userAccount.getRole().getRoleId() == 2L){
                authResult.setUserAccountId(iLecturesService.getByUserAccount(userAccount).getLectureId());
            }
            if (userAccount.getRole().getRoleId() == 3L){
                authResult.setUserAccountId(iStudentService.getByUserAccount(userAccount).getStudentId());
            }
            authResult.setRole(userAccountDto.getRole());
            authResult.setEmail(userAccountDto.getEmail());
            authResult.setNickName(userAccountDto.getNickName());
            return ResponseEntity.ok(authResult);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ForgotPasswordDto> forgotPassword(@RequestBody ForgotPasswordDto forgotPasswordDto) {

        UserAccount userAccount = userAccountService.getUserAccountByEmail(forgotPasswordDto.getEmail());

        if (userAccount == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        String resetCode = jwtUtilityService.createJWT(userAccount, 5);
        System.out.println("Reset code : " + resetCode);

        userAccountService.updateResetCode(resetCode, userAccount.getEmail());

        sendEmail(userAccount.getEmail(), resetCode);

        ForgotPasswordDto forgotPasswordResponse = modelMapper.map(userAccount, ForgotPasswordDto.class);

        return ResponseEntity.ok().body(forgotPasswordResponse);
    }

    @GetMapping("/reset-password/{resetCode}")
    public ResponseEntity<String> verifyResetCode(@PathVariable String resetCode) throws Exception {
        UserAccount userAccount = jwtUtilityService.verifyToken(resetCode);

        if (userAccount == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.ok("Valid reset code");
    }

    @PostMapping("/reset-password/{resetCode}")
    public ResponseEntity<NewPasswordDto> resetPassword(@PathVariable String resetCode, @RequestBody NewPasswordDto newPasswordDto) throws Exception {

        UserAccount verifyToken = jwtUtilityService.verifyToken(resetCode);
        UserAccount userAccount = userAccountService.getByResetCode(resetCode);

        if (verifyToken == null || userAccount == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }


        String newPassword = newPasswordDto.getNewPassword();
        String confirmNewPassword = newPasswordDto.getConfirmNewPassword();

        if (!newPassword.equals(confirmNewPassword)) {
            throw new IllegalStateException("New password not same confirm new password!");
        }

        userAccountService.updateResetCode(null, userAccount.getEmail());
        userAccountService.updatePassword(userAccount, newPassword);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);

    }

    private void sendEmail(String recipientEmail, String resetCode) {

        String resetLink = "http://localhost:4200/reset-password/" + resetCode;

        String subject = "Here's the link to reset your password";
        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href='"+ resetLink +"'>Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>"
                + "<p> This link will expire <strong style=\"color: red; font-size: 18px;\"> 5 </strong> minutes after the email is sent. </p>";
        mailServiceProvider.sendEmail(recipientEmail, subject, content);
    }


    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request, response);
    }
}
