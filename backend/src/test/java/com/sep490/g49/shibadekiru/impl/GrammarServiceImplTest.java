package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.Grammar;
import com.sep490.g49.shibadekiru.entity.Lesson;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.repository.GrammarRepository;
import com.sep490.g49.shibadekiru.repository.LessonRepository;
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

class GrammarServiceImplTest {

    @InjectMocks
    GrammarServiceImpl grammarService;

    @Mock
    GrammarRepository grammarRepository;

    @Mock
    LessonRepository lessonRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllGrammar() {
        // Mocking the behavior of the repository
        List<Grammar> mockGrammarList = new ArrayList<>();
        when(grammarRepository.findAll()).thenReturn(mockGrammarList);

        // Call the service method
        List<Grammar> result = grammarService.getAllGrammar();

        // Verify the result
        assertNotNull(result);
        assertEquals(mockGrammarList, result);
    }

    @Test
    void getGrammarById() {
        // Mocking the behavior of the repository
        long grammarId = 1L;
        Grammar mockGrammar = new Grammar();
        when(grammarRepository.findById(grammarId)).thenReturn(Optional.of(mockGrammar));

        // Call the service method
        Grammar result = grammarService.getGrammarById(grammarId);

        // Verify the result
        assertNotNull(result);
        assertEquals(mockGrammar, result);
    }

    @Test
    void createGrammar() {
        // Mocking the behavior of the repository and lessonRepository
        Grammar mockGrammar = new Grammar();
        Lesson mockLesson = new Lesson();
        when(lessonRepository.findById(anyLong())).thenReturn(Optional.of(mockLesson));
        when(grammarRepository.save(any())).thenReturn(mockGrammar);

        // Call the service method
        Grammar result = grammarService.createGrammar(mockGrammar);

        // Verify the result
        assertNotNull(result);
        assertEquals(mockGrammar, result);
    }

    @Test
    void updateGrammar() {
        // Mocking the behavior of the repository
        long grammarId = 1L;
        Grammar existingGrammar = new Grammar();
        Grammar updatedGrammar = new Grammar();
        when(grammarRepository.findById(grammarId)).thenReturn(Optional.of(existingGrammar));
        when(grammarRepository.save(any())).thenReturn(updatedGrammar);

        // Call the service method
        Grammar result = grammarService.updateGrammar(grammarId, updatedGrammar);

        // Verify the result
        assertNotNull(result);
        assertEquals(updatedGrammar, result);
    }

    @Test
    void deleteGrammar() {
        // Mocking the behavior of the repository
        long grammarId = 1L;
        Grammar existingGrammar = new Grammar();
        when(grammarRepository.findById(grammarId)).thenReturn(Optional.of(existingGrammar));

        // Call the service method
        grammarService.deleteGrammar(grammarId);

        // Verify that the delete method of the repository is called
        verify(grammarRepository, times(1)).delete(existingGrammar);
    }

    @Test
    void getGrammarPartByLesson() {
        // Mocking the behavior of the repository and lessonRepository
        Lesson mockLesson = new Lesson();
        List<Grammar> mockGrammarList = new ArrayList<>();
        when(lessonRepository.findById(anyLong())).thenReturn(Optional.of(mockLesson));
        when(grammarRepository.findGrammarByLesson(mockLesson)).thenReturn(mockGrammarList);

        // Call the service method
        List<Grammar> result = grammarService.getGrammarPartByLesson(mockLesson);

        // Verify the result
        assertNotNull(result);
        assertEquals(mockGrammarList, result);
    }
}
