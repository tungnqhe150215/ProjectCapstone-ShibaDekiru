package com.sep490.g49.shibadekiru.controller.auth;

import com.sep490.g49.shibadekiru.dto.*;
import com.sep490.g49.shibadekiru.entity.RoleType;
import com.sep490.g49.shibadekiru.impl.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationServiceImpl authenticationService;

    private final LecturesServiceImpl lecturesService;


    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterResponse request
    ) {
        try {
            request.setRoleId((3L));
            authenticationService.register(request);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Trả về thông báo lỗi nếu có lỗi xảy ra
        }

    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationDto> authenticate(
            @RequestBody AuthenticationLoginDto request
    ) {
        //return ResponseEntity.ok(authenticationService.authenticate(request));

        AuthenticationDto authResult = authenticationService.authenticate(request);

        if (authResult != null) {
            String accessToken = authResult.getAccessToken();
            UserAccountDto userAccountDto = authenticationService.getUserInfoByToken(accessToken);

            if (userAccountDto != null) {

                if (userAccountDto.getRole().getRoleType() == RoleType.LECTURE) {
                    Long lectureId = authenticationService.getLectureIdByMemberId(userAccountDto.getMemberId());
                    authResult.setLectureId(Long.valueOf(String.valueOf(lectureId)));
                }

                else if (userAccountDto.getRole().getRoleType() == RoleType.STUDENT) {
                    Long studentId = authenticationService.getStudentIdByMemberId(userAccountDto.getMemberId());
                    authResult.setStudentId(Long.valueOf(String.valueOf(studentId)));
                }

                System.out.println("Token: " + accessToken);
                System.out.println("Nick name: " + userAccountDto.getNickName());
                System.out.println("Member id: " + userAccountDto.getMemberId());
                System.out.println("Pass word:" + userAccountDto.getPassword());
                System.out.println("Role id: " + userAccountDto.getRole());
                System.out.println("US id: " + userAccountDto.getUserName());
                authResult.setRole(userAccountDto.getRole());
                authResult.setEmail(userAccountDto.getEmail());
                authResult.setUserAccountId(userAccountDto.getUserAccountId());
                authResult.setUserName(userAccountDto.getUserName());
                return ResponseEntity.ok(authResult);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request, response);
    }
}