package com.sep490.g49.shibadekiru.impl;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.sep490.g49.shibadekiru.service.GoogleDriveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLOutput;

@Service
public class GoogleDriveServiceImpl implements GoogleDriveService {

    @Autowired
    private Drive driveService;

    @Override
    public String uploadFileToGoogleDrive(MultipartFile file) {
        try {
            // Tạo tệp tạm thời từ MultipartFile
            java.io.File tempFile = convertMultipartFileToFile(file);

            File driveFile = new File();
            driveFile.setName(file.getOriginalFilename());

            // Tạo yêu cầu tải lên tệp trên Google Drive
            Drive.Files.Create createRequest = driveService.files().create(driveFile, new FileContent(file.getContentType(), tempFile)).setFields("id,webViewLink");

            // Thực hiện yêu cầu và lấy kết quả
            File uploadedFile = createRequest.execute();

            // Xóa tệp tạm thời sau khi tải lên
            tempFile.delete();

            return uploadedFile.getId(); // ID của tệp trên Google Drive
        } catch (IOException e) {
            // Xử lý lỗi
            return null;
        }
    }

    private java.io.File convertMultipartFileToFile(MultipartFile file) throws IOException {
        java.io.File tempFile = java.io.File.createTempFile("temp", null);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(file.getBytes());
        }
        return tempFile;
    }

    @Override
    public File getFileById(String fileId) {
        try {
            // Tạo yêu cầu để lấy tệp từ Google Drive bằng ID
            return driveService.files().get(fileId).execute();
        } catch (IOException e) {
            // Xử lý lỗi
            return null;
        }
    }

    @Override
    public String updateFile(String fileId, File updatedFile) {
        try {
            // Tạo yêu cầu cập nhật tệp trên Google Drive bằng ID
            return driveService.files().update(fileId, updatedFile).execute().getId();
        } catch (IOException e) {
            // Xử lý lỗi
            return null;
        }
    }

    @Override
    public void deleteFile(String fileId) {
        try {
            // Tạo yêu cầu xóa tệp từ Google Drive bằng ID
            driveService.files().delete(fileId).execute();
        } catch (IOException e) {
            // Xử lý lỗi
        }
    }

    @Override
    public String getFileUrl(String fileId) {
        try {
            File file = driveService.files().get(fileId).execute();
            String fileUrl = file.getWebContentLink();
            System.out.println(file);
            System.out.println("link "+file.getWebContentLink());
            return fileUrl;
        } catch (IOException e) {
            // Xử lý lỗi
            e.printStackTrace();
            return null;
        }
    }
}
