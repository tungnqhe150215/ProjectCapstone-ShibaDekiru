package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.Reading;
import com.sep490.g49.shibadekiru.entity.ReadingQuestion;
import com.sep490.g49.shibadekiru.repository.ReadingQuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReadingQuestionServiceImplTest {

    @Mock
    private ReadingQuestionRepository readingQuestionRepository;

    @InjectMocks
    private ReadingQuestionServiceImpl readingQuestionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getReadingQuestionByReading() {
        // Arrange
        Reading reading = new Reading();
        List<ReadingQuestion> readingQuestions = new ArrayList<>();
        readingQuestions.add(new ReadingQuestion());
        readingQuestions.add(new ReadingQuestion());
        when(readingQuestionRepository.findAllByReading(reading)).thenReturn(readingQuestions);

        // Act
        List<ReadingQuestion> result = readingQuestionService.getReadingQuestionByReading(reading);

        // Assert
        assertEquals(2, result.size());
        verify(readingQuestionRepository, times(1)).findAllByReading(reading);
    }

    @Test
    void getReadingQuestionById() {
        // Arrange
        Long readingQuestionId = 1L;
        ReadingQuestion readingQuestion = new ReadingQuestion();
        when(readingQuestionRepository.findReadingQuestionByReadingQuestionId(readingQuestionId)).thenReturn(readingQuestion);

        // Act
        ReadingQuestion result = readingQuestionService.getReadingQuestionById(readingQuestionId);

        // Assert
        assertEquals(readingQuestion, result);
    }

    @Test
    void createReadingQuestion() {
        // Arrange
        ReadingQuestion readingQuestionRequest = new ReadingQuestion();
        when(readingQuestionRepository.save(readingQuestionRequest)).thenReturn(readingQuestionRequest);

        // Act
        ReadingQuestion result = readingQuestionService.createReadingQuestion(readingQuestionRequest);

        // Assert
        assertNotNull(result);
        assertEquals(readingQuestionRequest, result);
        verify(readingQuestionRepository, times(1)).save(readingQuestionRequest);
    }

    @Test
    void updateReadingQuestion() {
        // Arrange
        Long readingQuestionId = 1L;
        ReadingQuestion existingReadingQuestion = new ReadingQuestion();
        ReadingQuestion updatedReadingQuestion = new ReadingQuestion();
        updatedReadingQuestion.setSampleAnswer("Updated Sample Answer");

        when(readingQuestionRepository.findReadingQuestionByReadingQuestionId(readingQuestionId)).thenReturn(existingReadingQuestion);
        when(readingQuestionRepository.save(any())).thenReturn(updatedReadingQuestion);

        // Act
        ReadingQuestion result = readingQuestionService.updateReadingQuestion(readingQuestionId, updatedReadingQuestion);

        // Assert
        assertNotNull(result);
        assertEquals(updatedReadingQuestion.getSampleAnswer(), result.getSampleAnswer());
        verify(readingQuestionRepository, times(1)).findReadingQuestionByReadingQuestionId(readingQuestionId);
        verify(readingQuestionRepository, times(1)).save(any());
    }

    @Test
    void deleteReadingQuestion() {
        // Arrange
        Long readingQuestionId = 1L;
        ReadingQuestion readingQuestion = new ReadingQuestion();
        when(readingQuestionRepository.findReadingQuestionByReadingQuestionId(readingQuestionId)).thenReturn(readingQuestion);

        // Act
        readingQuestionService.deleteReadingQuestion(readingQuestionId);

        // Assert
        verify(readingQuestionRepository, times(1)).findReadingQuestionByReadingQuestionId(readingQuestionId);
        verify(readingQuestionRepository, times(1)).delete(readingQuestion);
    }
}
