package com.sep490.g49.shibadekiru.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@Configuration
public class DriveConfigService {
    @Value("${google.drive.serviceAccountId}")
    private String serviceAccountId;

    @Value("${google.drive.serviceAccountPrivateKeyFile}")
    private String serviceAccountPrivateKeyFile;

    @Bean
    public Drive getDriveService() throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

        GoogleCredential credential = new GoogleCredential.Builder()
                .setTransport(HTTP_TRANSPORT)
                .setJsonFactory(JSON_FACTORY)
                .setServiceAccountId(serviceAccountId)
                .setServiceAccountScopes(Collections.singletonList( "https://www.googleapis.com/auth/drive"))
                .setServiceAccountPrivateKeyFromP12File(new File(serviceAccountPrivateKeyFile)) // Đường dẫn đến tệp P12
                .build();

        return new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName("Shiba Dekiru")
                .build();
    }
}
