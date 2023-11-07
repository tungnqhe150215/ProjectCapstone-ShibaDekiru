package com.sep490.g49.shibadekiru.controller.auth;

import com.sep490.g49.shibadekiru.dto.*;
import com.sep490.g49.shibadekiru.entity.Lectures;
import com.sep490.g49.shibadekiru.entity.UserAccount;
import com.sep490.g49.shibadekiru.impl.AuthenticationServiceImpl;
import com.sep490.g49.shibadekiru.impl.JWTServiceImpl;
import com.sep490.g49.shibadekiru.impl.LecturesServiceImpl;
import com.sep490.g49.shibadekiru.impl.RoleServiceImpl;
import com.sep490.g49.shibadekiru.service.ILecturesService;
import com.sep490.g49.shibadekiru.service.IStudentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationServiceImpl authenticationService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ILecturesService iLecturesService;

    @Autowired
    private IStudentService iStudentService;

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
            authResult.setUserName(userAccountDto.getUserName());
            return ResponseEntity.ok(authResult);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request, response);
    }
}
