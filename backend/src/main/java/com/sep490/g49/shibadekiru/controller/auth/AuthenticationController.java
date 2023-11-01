package com.sep490.g49.shibadekiru.controller.auth;

import com.sep490.g49.shibadekiru.dto.AuthenticationDto;
import com.sep490.g49.shibadekiru.dto.AuthenticationLoginDto;
import com.sep490.g49.shibadekiru.dto.ChangePasswordDto;
import com.sep490.g49.shibadekiru.dto.UserAccountDto;
import com.sep490.g49.shibadekiru.impl.AuthenticationServiceImpl;
import com.sep490.g49.shibadekiru.impl.JWTServiceImpl;
import com.sep490.g49.shibadekiru.impl.RoleServiceImpl;
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

   @Autowired
    private RoleServiceImpl roleService;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationDto> register(
            @RequestBody UserAccountDto request
    ) {
        request.setRole(roleService.getByRoleId(3L));
        return ResponseEntity.ok(authenticationService.register(request));
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
            authResult.setRole(userAccountDto.getRole());
            authResult.setEmail(userAccountDto.getEmail());
            authResult.setUserAccountId(userAccountDto.getUserAccountId());
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
