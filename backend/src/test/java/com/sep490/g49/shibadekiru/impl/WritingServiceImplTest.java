import com.sep490.g49.shibadekiru.entity.Lesson;
import com.sep490.g49.shibadekiru.entity.Writing;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.impl.WritingServiceImpl;
import com.sep490.g49.shibadekiru.repository.WritingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WritingServiceImplTest {

    @Mock
    private WritingRepository writingRepository;

    @InjectMocks
    private WritingServiceImpl writingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetWritingPartByLesson() {
        // Arrange
        Lesson lesson = new Lesson();
        Writing writing = new Writing();
        List<Writing> expectedList = new ArrayList<>();
        expectedList.add(writing);

        when(writingRepository.findByLesson(lesson)).thenReturn(expectedList);

        // Act
        List<Writing> result = writingService.getWritingPartByLesson(lesson);

        // Assert
        assertEquals(expectedList, result);
    }

    @Test
    void testGetWritingById() {
        // Arrange
        Long id = 1L;
        Writing expectedWriting = new Writing();

        when(writingRepository.findById(id)).thenReturn(Optional.of(expectedWriting));

        // Act
        Writing result = writingService.getWritingById(id);

        // Assert
        assertEquals(expectedWriting, result);
    }

    @Test
    void testGetWritingByIdNotFound() {
        // Arrange
        Long id = 1L;

        when(writingRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> writingService.getWritingById(id));
    }

    @Test
    void testCreateWriting() {
        // Arrange
        Writing writingRequest = new Writing();
        Writing expectedWriting = new Writing();

        when(writingRepository.save(writingRequest)).thenReturn(expectedWriting);

        // Act
        Writing result = writingService.createWriting(writingRequest);

        // Assert
        assertEquals(expectedWriting, result);
    }

    @Test
    void testUpdateWriting() {
        // Arrange
        Long id = 1L;
        Writing writingRequest = new Writing();
        Writing existingWriting = new Writing();

        when(writingRepository.findById(id)).thenReturn(Optional.of(existingWriting));
        when(writingRepository.save(existingWriting)).thenReturn(existingWriting);

        // Act
        Writing result = writingService.updateWriting(id, writingRequest);

        // Assert
        assertEquals(existingWriting, result);
        assertEquals(writingRequest.getTopic(), existingWriting.getTopic());
    }

    @Test
    void testUpdateWritingNotFound() {
        // Arrange
        Long id = 1L;
        Writing writingRequest = new Writing();

        when(writingRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> writingService.updateWriting(id, writingRequest));
    }

    @Test
    void testDeleteWriting() {
        // Arrange
        Long id = 1L;
        Writing existingWriting = new Writing();

        when(writingRepository.findById(id)).thenReturn(Optional.of(existingWriting));

        // Act
        writingService.deleteWriting(id);

        // Assert
        verify(writingRepository, times(1)).delete(existingWriting);
    }

    @Test
    void testDeleteWritingNotFound() {
        // Arrange
        Long id = 1L;

        when(writingRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> writingService.deleteWriting(id));
    }
}
