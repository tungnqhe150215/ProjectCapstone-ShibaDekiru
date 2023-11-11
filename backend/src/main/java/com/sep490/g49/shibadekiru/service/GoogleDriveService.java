package com.sep490.g49.shibadekiru.service;

import com.google.api.services.drive.model.File;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface GoogleDriveService {
    String uploadFileToGoogleDrive(MultipartFile file);

    void configurePublicSharing(String fileId) throws IOException;

    File getFileById(String fileId);

    String updateFile(String fileId, File updatedFile);

    void deleteFile(String fileId);

    String getFileUrl(String fileId);
}
