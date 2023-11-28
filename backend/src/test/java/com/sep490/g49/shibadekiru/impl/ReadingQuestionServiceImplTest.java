package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.Reading;
import com.sep490.g49.shibadekiru.entity.ReadingQuestion;
import com.sep490.g49.shibadekiru.repository.ReadingQuestionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class ReadingQuestionServiceImplTest {

    @Mock
    private ReadingQuestionRepository readingQuestionRepository;

    @InjectMocks
    private ReadingQuestionServiceImpl readingQuestionService;

    @Test
    void getReadingQuestionByReading() {
        // Mocking the behavior of readingQuestionRepository.findAllByReading() method
        Reading reading = new Reading();
        List<ReadingQuestion> mockQuestions = new ArrayList<>();
        Mockito.when(readingQuestionRepository.findAllByReading(reading)).thenReturn(mockQuestions);

        List<ReadingQuestion> result = readingQuestionService.getReadingQuestionByReading(reading);

        assertNotNull(result);
        assertEquals(0, result.size()); // Assuming the mockQuestions list is empty
    }

    @Test
    void getReadingQuestionById() {
        // Mocking the behavior of readingQuestionRepository.findReadingQuestionByReadingQuestionId() method
        Long questionId = 1L;
        ReadingQuestion expectedQuestion = new ReadingQuestion();
        Mockito.when(readingQuestionRepository.findReadingQuestionByReadingQuestionId(questionId)).thenReturn(expectedQuestion);

        ReadingQuestion result = readingQuestionService.getReadingQuestionById(questionId);

        assertNotNull(result);
        // Add more assertions as needed
    }

    @Test
    void createReadingQuestion() {
        // Mocking the behavior of readingQuestionRepository.save() method
        ReadingQuestion savedQuestion = new ReadingQuestion();
        Mockito.when(readingQuestionRepository.save(any(ReadingQuestion.class))).thenReturn(savedQuestion);

        ReadingQuestion result = readingQuestionService.createReadingQuestion(new ReadingQuestion());

        assertNotNull(result);
        // Add more assertions as needed
    }

    @Test
    void updateReadingQuestion() {
        Long questionId = 1L;
        ReadingQuestion updatedQuestion = new ReadingQuestion();

        // Mocking the behavior of readingQuestionRepository.findReadingQuestionByReadingQuestionId() method
        ReadingQuestion existingQuestion = new ReadingQuestion();
        Mockito.when(readingQuestionRepository.findReadingQuestionByReadingQuestionId(questionId)).thenReturn(existingQuestion);

        // Mocking the behavior of readingQuestionRepository.save() method
        ReadingQuestion savedQuestion = new ReadingQuestion();
        Mockito.when(readingQuestionRepository.save(any(ReadingQuestion.class))).thenReturn(savedQuestion);

        ReadingQuestion result = readingQuestionService.updateReadingQuestion(questionId, updatedQuestion);

        assertNotNull(result);
        // Add more assertions as needed
    }

    @Test
    void deleteReadingQuestion() {
        Long questionId = 1L;

        // Mocking the behavior of readingQuestionRepository.findReadingQuestionByReadingQuestionId() method
        ReadingQuestion existingQuestion = new ReadingQuestion();
        Mockito.when(readingQuestionRepository.findReadingQuestionByReadingQuestionId(questionId)).thenReturn(existingQuestion);

        assertDoesNotThrow(() -> readingQuestionService.deleteReadingQuestion(questionId));
    }
}
