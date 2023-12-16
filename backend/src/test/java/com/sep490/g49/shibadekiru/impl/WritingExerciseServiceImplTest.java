package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.ClassWork;
import com.sep490.g49.shibadekiru.entity.Exercise;
import com.sep490.g49.shibadekiru.entity.WritingExercise;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.impl.WritingExerciseServiceImpl;
import com.sep490.g49.shibadekiru.repository.ExerciseRepository;
import com.sep490.g49.shibadekiru.repository.WritingExerciseRepository;
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

class WritingExerciseServiceImplTest {

    @Mock
    private WritingExerciseRepository writingExerciseRepository;

    @Mock
    private ExerciseRepository exerciseRepository;

    @InjectMocks
    private WritingExerciseServiceImpl writingExerciseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getWritingExerciseByExercise() {
        // Mock data
        Exercise exercise = new Exercise();
        when(writingExerciseRepository.findWritingExerciseByExerciseAndIsDeletedFalse(exercise)).thenReturn(new ArrayList<>());

        // Test
        var result = writingExerciseService.getWritingExerciseByExercise(exercise);

        // Assertions
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getWritingExerciseById_WhenExists() {
        // Mock data
        Long id = 1L;
        WritingExercise writingExercise = new WritingExercise();
        when(writingExerciseRepository.findById(id)).thenReturn(Optional.of(writingExercise));

        // Test
        var result = writingExerciseService.getWritingExerciseById(id);

        // Assertions
        assertNotNull(result);
    }

    @Test
    void getWritingExerciseById_WhenNotExists() {
        // Mock data
        Long id = 1L;
        when(writingExerciseRepository.findById(id)).thenReturn(Optional.empty());

        // Test
        assertThrows(ResourceNotFoundException.class, () -> writingExerciseService.getWritingExerciseById(id));
    }

    @Test
    void createWritingExercise() {
        // Mock data
        WritingExercise writingExerciseRequest = new WritingExercise();
        when(writingExerciseRepository.save(any(WritingExercise.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Test
        var createdWritingExercise = writingExerciseService.createWritingExercise(writingExerciseRequest);

        // Assertions
        assertNotNull(createdWritingExercise);
    }

    @Test
    void updateWritingExercise_WhenExists() {
        // Mock data
        Long id = 1L;
        WritingExercise writingExerciseRequest = new WritingExercise();
        writingExerciseRequest.setQuestion("Updated question");
        WritingExercise existingWritingExercise = new WritingExercise();
        when(writingExerciseRepository.findById(id)).thenReturn(Optional.of(existingWritingExercise));
        when(writingExerciseRepository.save(any(WritingExercise.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Test
        var updatedWritingExercise = writingExerciseService.updateWritingExercise(id, writingExerciseRequest);

        // Assertions
        assertNotNull(updatedWritingExercise);
        assertEquals("Updated question", updatedWritingExercise.getQuestion());
    }

    @Test
    void updateWritingExercise_WhenNotExists() {
        // Mock data
        Long id = 1L;
        WritingExercise writingExerciseRequest = new WritingExercise();
        when(writingExerciseRepository.findById(id)).thenReturn(Optional.empty());

        // Test
        assertThrows(ResourceNotFoundException.class, () -> writingExerciseService.updateWritingExercise(id, writingExerciseRequest));
    }

    @Test
    void deleteWritingExercise_WhenExists() {
        // Mock data
        Long id = 1L;
        WritingExercise existingWritingExercise = new WritingExercise();
        when(writingExerciseRepository.findById(id)).thenReturn(Optional.of(existingWritingExercise));

        // Test
        assertDoesNotThrow(() -> writingExerciseService.deleteWritingExercise(id));
    }

    @Test
    void deleteWritingExercise_WhenNotExists() {
        // Mock data
        Long id = 1L;
        when(writingExerciseRepository.findById(id)).thenReturn(Optional.empty());

        // Test
        assertThrows(ResourceNotFoundException.class, () -> writingExerciseService.deleteWritingExercise(id));
    }

    @Test
    void getWritingExerciseByClasswork() {
        // Mock data
        ClassWork classWork = new ClassWork();
        when(exerciseRepository.findExercisesByClassWorkAndIsDeletedFalse(classWork)).thenReturn(new ArrayList<>());
        when(writingExerciseRepository.findWritingExerciseByExerciseAndIsDeletedFalse(any())).thenReturn(new ArrayList<>());

        // Test
        var result = writingExerciseService.getWritingExerciseByClasswork(classWork);

        // Assertions
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
