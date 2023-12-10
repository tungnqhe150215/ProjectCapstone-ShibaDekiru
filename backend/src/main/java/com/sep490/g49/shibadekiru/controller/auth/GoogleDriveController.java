package com.sep490.g49.shibadekiru.controller.auth;

import com.google.api.services.drive.model.Permission;
import com.sep490.g49.shibadekiru.dto.DriveDto;
import com.sep490.g49.shibadekiru.impl.UserAccountServiceImpl;
import com.sep490.g49.shibadekiru.service.GoogleDriveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/drive")
public class GoogleDriveController {

    @Autowired
    private GoogleDriveService googleDriveService;

    @Autowired
    private UserAccountServiceImpl userAccountService;

    @PostMapping("/upload")
    public ResponseEntity<DriveDto> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {

        // Xử lý tệp (file) ở đây
        if (!file.isEmpty()) {
            System.out.println("Đã có file");
            // Lưu tệp vào thư mục lưu trữ
            String fileId = googleDriveService.uploadFileToGoogleDrive(file);

            googleDriveService.configurePublicSharing(fileId);

            DriveDto driveDto = new DriveDto(fileId);

            return ResponseEntity.ok(driveDto);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/upload/my-profile")
    public ResponseEntity<DriveDto> uploadFile(@RequestParam("file") MultipartFile file, Principal connectedUser) throws IOException {
        if (!file.isEmpty() && isImageFile(file)) {
            // Lưu tệp vào thư mục lưu trữ
            String fileId = googleDriveService.uploadFileToGoogleDrive(file);

            // Cập nhật đường dẫn ảnh trong hồ sơ người dùng
            userAccountService.updateProfileByAvatar(fileId, connectedUser);

            System.out.println("Đã có file");

            // Cấu hình chia sẻ công khai cho file trên Google Drive
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
        return Arrays.asList("jpg", "jpeg", "png").contains(fileExtension);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFile(@PathVariable Long id) {
        // Xóa tệp theo id hoặc bất kỳ quy tắc xóa nào
        // ...

        return ResponseEntity.ok("File deleted successfully");
    }
}
