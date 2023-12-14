package com.sep490.g49.shibadekiru.controller.student;

import com.sep490.g49.shibadekiru.dto.ChangePasswordDto;
import com.sep490.g49.shibadekiru.dto.DriveDto;
import com.sep490.g49.shibadekiru.dto.LecturesDto;
import com.sep490.g49.shibadekiru.dto.StudentDto;
import com.sep490.g49.shibadekiru.entity.Lectures;
import com.sep490.g49.shibadekiru.entity.Student;
import com.sep490.g49.shibadekiru.impl.UserAccountServiceImpl;
import com.sep490.g49.shibadekiru.repository.StudentRepository;
import com.sep490.g49.shibadekiru.service.GoogleDriveService;
import com.sep490.g49.shibadekiru.service.ILecturesService;
import com.sep490.g49.shibadekiru.service.IStudentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;

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
        Student student = iStudentService.getStudentByStudentIdToGetAvatar(id);

        StudentDto studentDto = modelMapper.map(student, StudentDto.class);

        return ResponseEntity.ok().body(studentDto);

    }

    @GetMapping("/lecture/{id}")
    public ResponseEntity<LecturesDto> getLectureById(@PathVariable Long id) {
        Lectures lectures = iLecturesService.getLectureByIdToGetAvatar(id);

        LecturesDto lecturesDto = modelMapper.map(lectures, LecturesDto.class);

        return ResponseEntity.ok().body(lecturesDto);

    }

    @PutMapping("/student/my-profile")
    public ResponseEntity<StudentDto> updateProfileStudent(@RequestBody StudentDto studentDto, Principal connectedUser) {
        try {
            Student studentRequest = modelMapper.map(studentDto, Student.class);

            Student student = userAccountService.updateProfileStudent(studentRequest, connectedUser);

            StudentDto studentResponse = modelMapper.map(student, StudentDto.class);

            return ResponseEntity.ok().body(studentResponse);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @PutMapping("/lecture/my-profile")
    public ResponseEntity<LecturesDto> updateProfileLecture(@RequestBody LecturesDto lecturesDto, Principal connectedUser) {
        try {
            Lectures lecturesRequest = modelMapper.map(lecturesDto, Lectures.class);

            Lectures lectures = userAccountService.updateProfileLecture(lecturesRequest, connectedUser);

            LecturesDto lectureResponse = modelMapper.map(lectures, LecturesDto.class);

            return ResponseEntity.ok().body(lectureResponse);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }



}
