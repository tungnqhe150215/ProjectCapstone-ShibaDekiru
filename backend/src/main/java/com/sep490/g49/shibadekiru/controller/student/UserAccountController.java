package com.sep490.g49.shibadekiru.controller.student;

import com.sep490.g49.shibadekiru.dto.ChangePasswordDto;
import com.sep490.g49.shibadekiru.dto.LecturesDto;
import com.sep490.g49.shibadekiru.dto.StudentDto;
import com.sep490.g49.shibadekiru.impl.AuthenticationServiceImpl;
import com.sep490.g49.shibadekiru.impl.UserAccountServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/user-account")
@RequiredArgsConstructor
public class UserAccountController {

    @Autowired
    private UserAccountServiceImpl userAccountService;




    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDto request, Principal connectedUser) {
        try {
            userAccountService.changePassword(request, connectedUser);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/student/my-profile")
    public ResponseEntity<?> updateProfileStudent(@RequestBody StudentDto request, Principal connectedUser) {
        try {
            userAccountService.updateProfileStudent(request, connectedUser);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/lecture/my-profile")
    public ResponseEntity<?> updateProfileLecture(@RequestBody LecturesDto request, Principal connectedUser) {
        try {
            userAccountService.updateProfileLecture(request, connectedUser);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
