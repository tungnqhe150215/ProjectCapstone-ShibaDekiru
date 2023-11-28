import com.sep490.g49.shibadekiru.entity.Lesson;
import com.sep490.g49.shibadekiru.entity.Vocabulary;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.impl.VocabularyServiceImpl;
import com.sep490.g49.shibadekiru.repository.LessonRepository;
import com.sep490.g49.shibadekiru.repository.VocabularyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class VocabularyServiceImplTest {

    @Mock
    private VocabularyRepository vocabularyRepository;

    @Mock
    private LessonRepository lessonRepository;

    @InjectMocks
    private VocabularyServiceImpl vocabularyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllVocabulary() {
        // Mock data
        when(vocabularyRepository.findAll()).thenReturn(Collections.emptyList());

        // Test
        var result = vocabularyService.getAllVocabulary();

        // Assertions
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getVocabularyById_WhenVocabularyExists() {
        // Mock data
        Long vocabularyId = 1L;
        Vocabulary vocabulary = new Vocabulary();
        when(vocabularyRepository.findById(vocabularyId)).thenReturn(Optional.of(vocabulary));

        // Test
        var result = vocabularyService.getVocabularyById(vocabularyId);

        // Assertions
        assertNotNull(result);
        assertEquals(vocabulary, result);
    }

    @Test
    void getVocabularyById_WhenVocabularyNotExists() {
        // Mock data
        Long vocabularyId = 1L;
        when(vocabularyRepository.findById(vocabularyId)).thenReturn(Optional.empty());

        // Test and Assertions
        assertThrows(ResourceNotFoundException.class, () -> vocabularyService.getVocabularyById(vocabularyId));
    }

    @Test
    void createVocabulary() {
        // Mock data
        Vocabulary vocabularyRequest = new Vocabulary();
        vocabularyRequest.setVocabularyName("Test Vocabulary");
        vocabularyRequest.setHiragana("ひらがな");
        vocabularyRequest.setMeaning("Meaning of the vocabulary");
        vocabularyRequest.setExample("Example sentence");
        Lesson lesson = new Lesson();
        vocabularyRequest.setLesson(lesson);

        when(lessonRepository.findById(any())).thenReturn(Optional.of(lesson));
        when(vocabularyRepository.save(any(Vocabulary.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Test
        var createdVocabulary = vocabularyService.createVocabulary(vocabularyRequest);

        // Assertions
        assertNotNull(createdVocabulary);
        assertEquals("Test Vocabulary", createdVocabulary.getVocabularyName());
        assertEquals("ひらがな", createdVocabulary.getHiragana());
        assertEquals("Meaning of the vocabulary", createdVocabulary.getMeaning());
        assertEquals("Example sentence", createdVocabulary.getExample());
        assertEquals(lesson, createdVocabulary.getLesson());
    }

    @Test
    void createVocabulary_WhenLessonNotExists() {
        // Mock data
        Vocabulary vocabularyRequest = new Vocabulary();
        Lesson lesson = new Lesson();
        vocabularyRequest.setLesson(lesson);

        when(lessonRepository.findById(any())).thenReturn(Optional.empty());

        // Test and Assertions
        assertThrows(ResourceNotFoundException.class, () -> vocabularyService.createVocabulary(vocabularyRequest));
    }

    @Test
    void updateVocabulary_WhenVocabularyExists() {
        // Mock data
        Long vocabularyId = 1L;
        Vocabulary vocabularyUpdate = new Vocabulary();
        vocabularyUpdate.setVocabularyName("Updated Vocabulary");
        vocabularyUpdate.setHiragana("Updated ひらがな");
        vocabularyUpdate.setMeaning("Updated meaning");
        vocabularyUpdate.setExample("Updated example");
        Lesson updatedLesson = new Lesson();
        vocabularyUpdate.setLesson(updatedLesson);

        Vocabulary existingVocabulary = new Vocabulary();
        existingVocabulary.setVocabularyId(vocabularyId);
        existingVocabulary.setVocabularyName("Original Vocabulary");
        existingVocabulary.setHiragana("Original ひらがな");
        existingVocabulary.setMeaning("Original meaning");
        existingVocabulary.setExample("Original example");
        Lesson originalLesson = new Lesson();
        existingVocabulary.setLesson(originalLesson);

        when(vocabularyRepository.findById(vocabularyId)).thenReturn(Optional.of(existingVocabulary));
        when(vocabularyRepository.save(any(Vocabulary.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Test
        var updatedVocabulary = vocabularyService.updateVocabulary(vocabularyId, vocabularyUpdate);

        // Assertions
        assertNotNull(updatedVocabulary);
        assertEquals(vocabularyId, updatedVocabulary.getVocabularyId());
        assertEquals("Updated Vocabulary", updatedVocabulary.getVocabularyName());
        assertEquals("Updated ひらがな", updatedVocabulary.getHiragana());
        assertEquals("Updated meaning", updatedVocabulary.getMeaning());
        assertEquals("Updated example", updatedVocabulary.getExample());
        assertEquals(updatedLesson, updatedVocabulary.getLesson());
    }

    @Test
    void updateVocabulary_WhenVocabularyNotExists() {
        // Mock data
        Long vocabularyId = 1L;
        Vocabulary vocabularyUpdate = new Vocabulary();
        when(vocabularyRepository.findById(vocabularyId)).thenReturn(Optional.empty());

        // Test and Assertions
        assertThrows(ResourceNotFoundException.class, () -> vocabularyService.updateVocabulary(vocabularyId, vocabularyUpdate));
    }

    @Test
    void deleteVocabulary_WhenVocabularyExists() {
        // Mock data
        Long vocabularyId = 1L;
        Vocabulary existingVocabulary = new Vocabulary();
        when(vocabularyRepository.findById(vocabularyId)).thenReturn(Optional.of(existingVocabulary));

        // Test
        assertDoesNotThrow(() -> vocabularyService.deleteVocabulary(vocabularyId));

        // Verify
        verify(vocabularyRepository, times(1)).delete(existingVocabulary);
    }

    @Test
    void deleteVocabulary_WhenVocabularyNotExists() {
        // Mock data
        Long vocabularyId = 1L;
        when(vocabularyRepository.findById(vocabularyId)).thenReturn(Optional.empty());

        // Test and Assertions
        assertThrows(ResourceNotFoundException.class, () -> vocabularyService.deleteVocabulary(vocabularyId));
    }

    @Test
    void getVocabularyPartByLesson() {
        // Mock data
        Lesson lesson = new Lesson();
        when(vocabularyRepository.findVocabularyByLesson(lesson)).thenReturn(Collections.emptyList());

        // Test
        var result = vocabularyService.getVocabularyPartByLesson(lesson);

        // Assertions
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
