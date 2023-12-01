package com.sep490.g49.shibadekiru.impl;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.Drive.Permissions;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;
import com.google.api.services.drive.model.PermissionList;
import com.sep490.g49.shibadekiru.impl.GoogleDriveServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GoogleDriveServiceImplTest {

    @InjectMocks
    private GoogleDriveServiceImpl googleDriveService;

    @Mock
    private Drive driveService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void uploadFileToGoogleDrive() throws IOException {
        // Mock data
        MultipartFile file = new MockMultipartFile("test.txt", "Hello, World!".getBytes());
        InputStream inputStream = new ByteArrayInputStream("Hello, World!".getBytes());
        File driveFile = new File();
        driveFile.setId("123");
        driveFile.setWebViewLink("https://drive.google.com/123");

        // Mock behavior
        Files.Create createRequest = mock(Files.Create.class);
        when(driveService.files().create(any(File.class), any(com.google.api.client.http.FileContent.class))).thenReturn(createRequest);
        when(createRequest.setFields(anyString())).thenReturn(createRequest);
        when(createRequest.execute()).thenReturn(driveFile);

        // Call the method
        String result = googleDriveService.uploadFileToGoogleDrive(file);

        // Assertions
        assertNotNull(result);

        // Verify interactions
        verify(driveService.files(), times(1)).create(any(File.class), any(com.google.api.client.http.FileContent.class));
        verify(createRequest, times(1)).setFields("id,webViewLink");
        verify(createRequest, times(1)).execute();
    }

    @Test
    void configurePublicSharing() throws IOException {
        // Mock data
        String fileId = "123";

        // Mock behavior
        Permissions.Create createRequest = mock(Permissions.Create.class);
        when(driveService.permissions().create(eq(fileId), any(Permission.class))).thenReturn(createRequest);

        // Call the method
        googleDriveService.configurePublicSharing(fileId);

        // Verify interactions
        verify(driveService.permissions(), times(1)).create(eq(fileId), any(Permission.class));
        verify(createRequest, times(1)).execute();
    }

    @Test
    void getFileById() throws IOException {
        // Mock data
        String fileId = "123";
        File expectedFile = new File();
        expectedFile.setId(fileId);

        // Mock behavior
        when(driveService.files().get(fileId)).thenReturn(mock(Files.Get.class));
        when(driveService.files().get(fileId).execute()).thenReturn(expectedFile);

        // Call the method
        File result = googleDriveService.getFileById(fileId);

        // Assertions
        assertNotNull(result);
        assertEquals(expectedFile, result);

        // Verify interactions
        verify(driveService.files(), times(1)).get(fileId);
        verify(driveService.files().get(fileId), times(1)).execute();
    }

    @Test
    void updateFile() throws IOException {
        // Mock data
        String fileId = "123";
        File updatedFile = new File();
        updatedFile.setId(fileId);

        // Mock behavior
        when(driveService.files().update(eq(fileId), any(File.class))).thenReturn(mock(Files.Update.class));
        when(driveService.files().update(eq(fileId), any(File.class)).execute()).thenReturn(updatedFile);

        // Call the method
        String result = googleDriveService.updateFile(fileId, updatedFile);

        // Assertions
        assertNotNull(result);
        assertEquals(fileId, result);

        // Verify interactions
        verify(driveService.files(), times(1)).update(eq(fileId), any(File.class));
        verify(driveService.files().update(eq(fileId), any(File.class)), times(1)).execute();
    }

    @Test
    void deleteFile() throws IOException {
        // Mock data
        String fileId = "123";

        // Call the method
        googleDriveService.deleteFile(fileId);

        // Verify interactions
        verify(driveService.files(), times(1)).delete(fileId);
        verify(driveService.files().delete(fileId), times(1)).execute();
    }

    @Test
    void getFileUrl() throws IOException {
        // Mock data
        String fileId = "123";
        File file = new File();
        file.setWebContentLink("https://drive.google.com/123");

        // Mock behavior
        when(driveService.files().get(fileId)).thenReturn(mock(Files.Get.class));
        when(driveService.files().get(fileId).setFields("webContentLink")).thenReturn(driveService.files().get(fileId));
        when(driveService.files().get(fileId).execute()).thenReturn(file);

        // Call the method
        String result = googleDriveService.getFileUrl(fileId);

        // Assertions
        assertNotNull(result);
        assertEquals(file.getWebContentLink(), result);

        // Verify interactions
        verify(driveService.files(), times(1)).get(fileId);
        verify(driveService.files().get(fileId), times(1)).setFields("webContentLink");
        verify(driveService.files().get(fileId), times(1)).execute();
    }
}
