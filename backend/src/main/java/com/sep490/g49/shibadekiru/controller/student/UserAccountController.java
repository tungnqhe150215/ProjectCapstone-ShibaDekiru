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

    @Autowired
    private GoogleDriveService googleDriveService;

    @Autowired
    private StudentRepository studentRepository;



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
        Lectures lectures = iLecturesService.getLectureById(id);

        LecturesDto lecturesDto = modelMapper.map(lectures, LecturesDto.class);

        return ResponseEntity.ok().body(lecturesDto);

    }

    @PostMapping("/upload")
    public ResponseEntity<DriveDto> uploadFile(@RequestParam("file") MultipartFile file, Principal connectedUser) throws IOException {
        if (!file.isEmpty() && isImageFile(file)) {

            // Lưu tệp vào thư mục lưu trữ
            String fileId = googleDriveService.uploadFileToGoogleDrive(file);

            // Cập nhật đường dẫn ảnh trong bảng Student
            userAccountService.updateProfileStudentByAvatar(fileId, connectedUser);

            System.out.println("Đã có file");

            googleDriveService.configurePublicSharing(fileId);

            DriveDto driveDto = new DriveDto(fileId);

            return ResponseEntity.ok(driveDto);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    private boolean isImageFile(MultipartFile file) {
        // Kiểm tra phần mở rộng của tệp để xác định xem nó có phải là hình ảnh không
        String fileName = file.getOriginalFilename();
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        System.out.println("File extension: " + fileExtension);
        return Arrays.asList("jpg", "jpeg", "png", "gif").contains(fileExtension);
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
