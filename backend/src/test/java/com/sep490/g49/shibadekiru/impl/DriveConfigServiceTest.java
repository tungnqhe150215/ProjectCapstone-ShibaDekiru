import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.sep490.g49.shibadekiru.impl.DriveConfigService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class DriveConfigServiceTest {

    @InjectMocks
    private DriveConfigService driveConfigService;

    @Mock
    private GoogleNetHttpTransport googleNetHttpTransport;

    @Mock
    private GoogleCredential.Builder googleCredentialBuilder;

    @Mock
    private Drive.Builder driveBuilder;

    @Test
    void getDriveService() throws GeneralSecurityException, IOException {
        // Mock data
        final NetHttpTransport HTTP_TRANSPORT = mock(NetHttpTransport.class);
        GoogleCredential googleCredential = mock(GoogleCredential.class);
        Drive drive = mock(Drive.class);

        // Mock behavior
        when(googleNetHttpTransport.newTrustedTransport()).thenReturn(HTTP_TRANSPORT);
        when(googleCredentialBuilder.setTransport(HTTP_TRANSPORT)).thenReturn(googleCredentialBuilder);
        when(googleCredentialBuilder.setJsonFactory(GsonFactory.getDefaultInstance())).thenReturn(googleCredentialBuilder);
        when(googleCredentialBuilder.setServiceAccountId(anyString())).thenReturn(googleCredentialBuilder);
        when(googleCredentialBuilder.setServiceAccountScopes(Collections.singletonList(DriveScopes.DRIVE))).thenReturn(googleCredentialBuilder);
        when(googleCredentialBuilder.setServiceAccountPrivateKeyFromP12File(any(File.class))).thenReturn(googleCredentialBuilder);
        when(googleCredentialBuilder.build()).thenReturn(googleCredential);

        //when(driveBuilder.setTransport(HTTP_TRANSPORT)).thenReturn(driveBuilder);
        //when(driveBuilder.setJsonFactory(GsonFactory.getDefaultInstance())).thenReturn(driveBuilder);
        //when(driveBuilder.setCredential(googleCredential)).thenReturn(driveBuilder);
        when(driveBuilder.setApplicationName("Shiba Dekiru")).thenReturn(driveBuilder);
        when(driveBuilder.build()).thenReturn(drive);

        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Call the method
        Drive result = driveConfigService.getDriveService();

        // Assertions
        assertNotNull(result);

        // Verify interactions
        verify(googleNetHttpTransport, times(1)).newTrustedTransport();
        verify(googleCredentialBuilder, times(1)).setTransport(HTTP_TRANSPORT);
        verify(googleCredentialBuilder, times(1)).setJsonFactory(GsonFactory.getDefaultInstance());
        verify(googleCredentialBuilder, times(1)).setServiceAccountId(anyString());
        verify(googleCredentialBuilder, times(1)).setServiceAccountScopes(Collections.singletonList(DriveScopes.DRIVE));
        verify(googleCredentialBuilder, times(1)).setServiceAccountPrivateKeyFromP12File(any(File.class));
        verify(googleCredentialBuilder, times(1)).build();
        //verify(driveBuilder, times(1)).setTransport(HTTP_TRANSPORT);
        //verify(driveBuilder, times(1)).setJsonFactory(GsonFactory.getDefaultInstance());
        //verify(driveBuilder, times(1)).setCredential(googleCredential);
        verify(driveBuilder, times(1)).setApplicationName("Shiba Dekiru");
        verify(driveBuilder, times(1)).build();
    }
}
