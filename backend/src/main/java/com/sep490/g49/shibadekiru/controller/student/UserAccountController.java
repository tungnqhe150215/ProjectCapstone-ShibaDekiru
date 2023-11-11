package com.sep490.g49.shibadekiru.controller.student;

import com.sep490.g49.shibadekiru.dto.ChangePasswordDto;
import com.sep490.g49.shibadekiru.dto.LecturesDto;
import com.sep490.g49.shibadekiru.dto.StudentDto;
import com.sep490.g49.shibadekiru.dto.UserAccountDto;
import com.sep490.g49.shibadekiru.entity.Lectures;
import com.sep490.g49.shibadekiru.entity.Student;
import com.sep490.g49.shibadekiru.entity.UserAccount;
import com.sep490.g49.shibadekiru.impl.AuthenticationServiceImpl;
import com.sep490.g49.shibadekiru.impl.UserAccountServiceImpl;
import com.sep490.g49.shibadekiru.service.ILecturesService;
import com.sep490.g49.shibadekiru.service.IStudentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    private ModelMapper modelMapper;

    @Autowired
    private UserAccountServiceImpl userAccountService;

    @Autowired
    private ILecturesService iLecturesService;

    @Autowired
    private IStudentService iStudentService;



    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDto request, Principal connectedUser) {
        try {
            userAccountService.changePassword(request, connectedUser);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/student/{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable Long id) {
        Student student = iStudentService.getStudentByStudentId(id);

        StudentDto studentDto = modelMapper.map(student, StudentDto.class);

        return ResponseEntity.ok().body(studentDto);

    }

    @GetMapping("/lecture/{id}")
    public ResponseEntity<LecturesDto> getLectureById(@PathVariable Long id) {
        Lectures lectures = iLecturesService.getLectureById(id);

        LecturesDto lecturesDto = modelMapper.map(lectures, LecturesDto.class);

        return ResponseEntity.ok().body(lecturesDto);

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
