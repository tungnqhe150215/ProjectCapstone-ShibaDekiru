import com.sep490.g49.shibadekiru.entity.Writing;
import com.sep490.g49.shibadekiru.entity.WritingQuestion;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.impl.WritingQuestionServiceImpl;
import com.sep490.g49.shibadekiru.repository.WritingQuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class WritingQuestionServiceImplTest {

    @Mock
    private WritingQuestionRepository writingQuestionRepository;

    @InjectMocks
    private WritingQuestionServiceImpl writingQuestionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getWritingQuestionByWriting() {
        // Mock data
        Writing writing = new Writing();
        when(writingQuestionRepository.findAllByWriting(writing)).thenReturn(new ArrayList<>());

        // Test
        var result = writingQuestionService.getWritingQuestionByWriting(writing);

        // Assertions
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getWritingQuestionById_WhenExists() {
        // Mock data
        Long id = 1L;
        WritingQuestion writingQuestion = new WritingQuestion();
        when(writingQuestionRepository.findById(id)).thenReturn(Optional.of(writingQuestion));

        // Test
        var result = writingQuestionService.getWritingQuestionById(id);

        // Assertions
        assertNotNull(result);
    }

    @Test
    void getWritingQuestionById_WhenNotExists() {
        // Mock data
        Long id = 1L;
        when(writingQuestionRepository.findById(id)).thenReturn(Optional.empty());

        // Test
        assertThrows(ResourceNotFoundException.class, () -> writingQuestionService.getWritingQuestionById(id));
    }

    @Test
    void createWritingQuestion() {
        // Mock data
        WritingQuestion writingQuestionRequest = new WritingQuestion();
        when(writingQuestionRepository.save(any(WritingQuestion.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Test
        var createdWritingQuestion = writingQuestionService.createWritingQuestion(writingQuestionRequest);

        // Assertions
        assertNotNull(createdWritingQuestion);
    }

    @Test
    void updateWritingQuestion_WhenExists() {
        // Mock data
        Long id = 1L;
        WritingQuestion writingQuestionRequest = new WritingQuestion();
        writingQuestionRequest.setQuestion("Updated question");
        writingQuestionRequest.setSampleAnswer("Updated sample answer");
        WritingQuestion existingWritingQuestion = new WritingQuestion();
        when(writingQuestionRepository.findById(id)).thenReturn(Optional.of(existingWritingQuestion));
        when(writingQuestionRepository.save(any(WritingQuestion.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Test
        var updatedWritingQuestion = writingQuestionService.updateWritingQuestion(id, writingQuestionRequest);

        // Assertions
        assertNotNull(updatedWritingQuestion);
        assertEquals("Updated question", updatedWritingQuestion.getQuestion());
        assertEquals("Updated sample answer", updatedWritingQuestion.getSampleAnswer());
    }

    @Test
    void updateWritingQuestion_WhenNotExists() {
        // Mock data
        Long id = 1L;
        WritingQuestion writingQuestionRequest = new WritingQuestion();
        when(writingQuestionRepository.findById(id)).thenReturn(Optional.empty());

        // Test
        assertThrows(ResourceNotFoundException.class, () -> writingQuestionService.updateWritingQuestion(id, writingQuestionRequest));
    }

    @Test
    void deleteWritingQuestion_WhenExists() {
        // Mock data
        Long id = 1L;
        WritingQuestion existingWritingQuestion = new WritingQuestion();
        when(writingQuestionRepository.findById(id)).thenReturn(Optional.of(existingWritingQuestion));

        // Test
        assertDoesNotThrow(() -> writingQuestionService.deleteWritingQuestion(id));
    }

    @Test
    void deleteWritingQuestion_WhenNotExists() {
        // Mock data
        Long id = 1L;
        when(writingQuestionRepository.findById(id)).thenReturn(Optional.empty());

        // Test
        assertThrows(ResourceNotFoundException.class, () -> writingQuestionService.deleteWritingQuestion(id));
    }
}
