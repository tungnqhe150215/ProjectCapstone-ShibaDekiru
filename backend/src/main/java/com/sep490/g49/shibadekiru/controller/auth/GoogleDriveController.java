package com.sep490.g49.shibadekiru.controller.auth;

import com.google.api.services.drive.model.Permission;
import com.sep490.g49.shibadekiru.dto.DriveDto;
import com.sep490.g49.shibadekiru.service.GoogleDriveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/drive")
public class GoogleDriveController {

    @Autowired
    private GoogleDriveService googleDriveService;

    @PostMapping("/upload")
    public ResponseEntity<DriveDto> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {

        // Xử lý tệp (file) ở đây
        if (!file.isEmpty()) {
            System.out.println("Đã có file");
            // Lưu tệp vào thư mục lưu trữ
            String fileId = googleDriveService.uploadFileToGoogleDrive(file);

            googleDriveService.configurePublicSharing(fileId);
            System.out.println(fileId);
            DriveDto driveDto = new DriveDto(fileId);

            return ResponseEntity.ok(driveDto);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFile(@PathVariable Long id) {
        // Xóa tệp theo id hoặc bất kỳ quy tắc xóa nào
        // ...

        return ResponseEntity.ok("File deleted successfully");
    }
}
