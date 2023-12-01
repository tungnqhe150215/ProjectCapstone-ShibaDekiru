import com.sep490.g49.shibadekiru.entity.SectionType;
import com.sep490.g49.shibadekiru.entity.Test;
import com.sep490.g49.shibadekiru.entity.TestSection;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.impl.TestSectionServiceImpl;
import com.sep490.g49.shibadekiru.repository.TestSectionRepository;
import com.sep490.g49.shibadekiru.service.GoogleDriveService;
import org.junit.jupiter.api.BeforeEach;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class TestSectionServiceImplTest {

    @Mock
    private TestSectionRepository testSectionRepository;

    @Mock
    private GoogleDriveService googleDriveService;

    @InjectMocks
    private TestSectionServiceImpl testSectionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @org.junit.jupiter.api.Test
    void getTestSectionById_WithExistingTestSection_ReturnsTestSection() {
        // Arrange
        Long testSectionId = 1L;
        TestSection existingTestSection = new TestSection();
        when(testSectionRepository.findById(testSectionId)).thenReturn(Optional.of(existingTestSection));

        // Act
        TestSection result = testSectionService.getTestSectionById(testSectionId);

        // Assert
        assertThat(result).isSameAs(existingTestSection);
    }

    @org.junit.jupiter.api.Test
    void getTestSectionById_WithNonexistentTestSection_ThrowsResourceNotFoundException() {
        // Arrange
        Long testSectionId = 1L;
        when(testSectionRepository.findById(testSectionId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> testSectionService.getTestSectionById(testSectionId))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @org.junit.jupiter.api.Test
    void createTestSection_WithValidData_ReturnsCreatedTestSection() {
        // Arrange
        TestSection inputTestSection = new TestSection();
        when(testSectionRepository.save(any())).thenReturn(inputTestSection);

        // Act
        TestSection result = testSectionService.createTestSection(inputTestSection);

        // Assert
        assertThat(result).isSameAs(inputTestSection);
    }

    @org.junit.jupiter.api.Test
    void updateTestSection_WithExistingTestSection_ReturnsUpdatedTestSection() {
        // Arrange
        Long testSectionId = 1L;
        TestSection updatedTestSection = new TestSection();
        updatedTestSection.setSectionName("Updated Section");
        Optional<TestSection> existingTestSection = Optional.of(new TestSection());
        when(testSectionRepository.findById(testSectionId)).thenReturn(existingTestSection);
        when(testSectionRepository.save(any())).thenReturn(updatedTestSection);

        // Act
        TestSection result = testSectionService.updateTestSection(testSectionId, updatedTestSection);

        // Assert
        assertThat(result).isSameAs(updatedTestSection);
    }

    @org.junit.jupiter.api.Test
    void updateTestSection_WithNonexistentTestSection_ThrowsResourceNotFoundException() {
        // Arrange
        Long testSectionId = 1L;
        TestSection updatedTestSection = new TestSection();
        when(testSectionRepository.findById(testSectionId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> testSectionService.updateTestSection(testSectionId, updatedTestSection))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @org.junit.jupiter.api.Test
    void deleteTestSection_WithExistingTestSection_DeletesTestSection() {
        // Arrange
        Long testSectionId = 1L;
        TestSection existingTestSection = new TestSection();
        existingTestSection.setSectionType(SectionType.LISTENING);
        when(testSectionRepository.findById(testSectionId)).thenReturn(Optional.of(existingTestSection));

        // Act
        testSectionService.deleteTestSection(testSectionId);

        // Assert
        verify(googleDriveService, times(1)).deleteFile(existingTestSection.getSectionAttach());
        verify(testSectionRepository, times(1)).delete(existingTestSection);
    }

    @org.junit.jupiter.api.Test
    void deleteTestSection_WithNonexistentTestSection_ThrowsResourceNotFoundException() {
        // Arrange
        Long testSectionId = 1L;
        when(testSectionRepository.findById(testSectionId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> testSectionService.deleteTestSection(testSectionId))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}