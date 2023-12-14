import com.sep490.g49.shibadekiru.entity.Book;
import com.sep490.g49.shibadekiru.entity.Lesson;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.impl.LessonServiceImpl;
import com.sep490.g49.shibadekiru.repository.BookRepository;
import com.sep490.g49.shibadekiru.repository.LessonRepository;
import com.sep490.g49.shibadekiru.service.GoogleDriveService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LessonServiceImplTest {

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private GoogleDriveService googleDriveService;

    @InjectMocks
    private LessonServiceImpl lessonService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllLessons() {
        // Mock data
        List<Lesson> lessons = new ArrayList<>();
        when(lessonRepository.findAll()).thenReturn(lessons);

        // Mock behavior of GoogleDriveService
        when(googleDriveService.getFileUrl(anyString())).thenReturn("mocked-url");

        // Test
        List<Lesson> result = lessonService.getAllLessons();

        // Verify
        assertNotNull(result);
        assertEquals(lessons, result);
        verify(googleDriveService, times(lessons.size())).getFileUrl(anyString());
    }

    @Test
    void testGetLessonPartByBook() {
        // Mock data
        Book book = new Book();
        List<Lesson> lessons = new ArrayList<>();
        when(lessonRepository.findByBook(book)).thenReturn(lessons);

        // Mock behavior of GoogleDriveService
        when(googleDriveService.getFileUrl(anyString())).thenReturn("mocked-url");

        // Test
        List<Lesson> result = lessonService.getLessonPartByBook(book);

        // Verify
        assertNotNull(result);
        assertEquals(lessons, result);
        verify(googleDriveService, times(lessons.size())).getFileUrl(anyString());
    }

    // Write similar tests for other methods (createLesson, updateLesson, deleteLesson, getLessonById)

    // ...

}
