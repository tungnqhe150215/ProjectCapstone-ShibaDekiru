package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.*;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.impl.WritingExerciseAnswerServiceImpl;
import com.sep490.g49.shibadekiru.repository.*;
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

class WritingExerciseAnswerServiceImplTest {

    @Mock
    private WritingExerciseAnswerRepository writingExerciseAnswerRepository;

    @Mock
    private WritingExerciseRepository writingExerciseRepository;

    @Mock
    private ExerciseRepository exerciseRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private ClassWorkRepository classWorkRepository;

    @InjectMocks
    private WritingExerciseAnswerServiceImpl writingExerciseAnswerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createWritingExerciseAnswer() {
        // Mock data
        WritingExerciseAnswer writingExerciseAnswerRequest = new WritingExerciseAnswer();
        when(writingExerciseAnswerRepository.save(any(WritingExerciseAnswer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Test
        var createdWritingExerciseAnswer = writingExerciseAnswerService.createWritingExerciseAnswer(writingExerciseAnswerRequest);

        // Assertions
        assertNotNull(createdWritingExerciseAnswer);
    }

    @Test
    void updateWritingExerciseAnswer() {
        // Mock data
        WritingExerciseAnswer writingExerciseAnswerRequest = new WritingExerciseAnswer();
        writingExerciseAnswerRequest.setStudent(new Student());
        writingExerciseAnswerRequest.setWritingExercise(new WritingExercise());
        when(writingExerciseAnswerRepository.findByStudentAndAndWritingExercise(any(), any())).thenReturn(writingExerciseAnswerRequest);
        when(writingExerciseAnswerRepository.save(any(WritingExerciseAnswer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Test
        var updatedWritingExerciseAnswer = writingExerciseAnswerService.updateWritingExerciseAnswer(writingExerciseAnswerRequest);

        // Assertions
        assertNotNull(updatedWritingExerciseAnswer);
    }

    @Test
    void getWritingExerciseAnswerByExerciseAndStudent() {
        // Mock data
        Long exerciseId = 1L;
        Long studentId = 2L;
        Exercise exercise = new Exercise();
        Student student = new Student();
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(exerciseRepository.findByExerciseId(exerciseId)).thenReturn(exercise);
        when(writingExerciseRepository.findWritingExerciseByExercise(exercise)).thenReturn(new ArrayList<>());

        // Test
        var result = writingExerciseAnswerService.getWritingExerciseAnswerByExerciseAndStudent(exerciseId, studentId);

        // Assertions
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getWritingExerciseAnswerByClassworkAndStudent() {
        // Mock data
        Long classworkId = 1L;
        Long studentId = 2L;
        ClassWork classWork = new ClassWork();
        Student student = new Student();
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(classWorkRepository.findById(classworkId)).thenReturn(Optional.of(classWork));
        when(exerciseRepository.findExercisesByClassWork(classWork)).thenReturn(new ArrayList<>());

        // Test
        var result = writingExerciseAnswerService.getWritingExerciseAnswerByClassworkAndStudent(classworkId, studentId);

        // Assertions
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void checkWritingExerciseAnswerExist_WhenExist() {
        // Mock data
        Student student = new Student();
        WritingExercise writingExercise = new WritingExercise();
        when(writingExerciseAnswerRepository.findByStudentAndAndWritingExercise(student, writingExercise)).thenReturn(new WritingExerciseAnswer());

        // Test
        var result = writingExerciseAnswerService.checkWritingExerciseAnswerExist(student, writingExercise);

        // Assertions
        assertTrue(result);
    }

    @Test
    void checkWritingExerciseAnswerExist_WhenNotExist() {
        // Mock data
        Student student = new Student();
        WritingExercise writingExercise = new WritingExercise();
        when(writingExerciseAnswerRepository.findByStudentAndAndWritingExercise(student, writingExercise)).thenReturn(null);

        // Test
        var result = writingExerciseAnswerService.checkWritingExerciseAnswerExist(student, writingExercise);

        // Assertions
        assertFalse(result);
    }
}
