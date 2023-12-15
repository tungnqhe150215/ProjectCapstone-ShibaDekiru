package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.Lectures;
import com.sep490.g49.shibadekiru.entity.Test;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.impl.TestServiceImpl;
import com.sep490.g49.shibadekiru.repository.LecturersRepository;
import com.sep490.g49.shibadekiru.repository.TestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class TestServiceImplTest {

    @Mock
    private LecturersRepository lecturersRepository;

    @Mock
    private TestRepository testRepository;

    @InjectMocks
    private TestServiceImpl testService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @org.junit.jupiter.api.Test
    void getAllTestByLecture_ReturnsTests() {
        // Arrange
        Lectures lecture = new Lectures();
        List<Test> mockTests = new ArrayList<>();
        when(testRepository.findAllByLecture(lecture)).thenReturn(mockTests);

        // Act
        List<Test> result = testService.getAllTestByLecture(lecture);

        // Assert
        assertThat(result).isSameAs(mockTests);
    }

    @org.junit.jupiter.api.Test
    void getTestById_WithExistingTest_ReturnsTest() {
        // Arrange
        Long testId = 1L;
        Test existingTest = new Test();
        when(testRepository.findById(testId)).thenReturn(Optional.of(existingTest));

        // Act
        Test result = testService.getTestById(testId);

        // Assert
        assertThat(result).isSameAs(existingTest);
    }

    @org.junit.jupiter.api.Test
    void getTestById_WithNonexistentTest_ThrowsResourceNotFoundException() {
        // Arrange
        Long testId = 1L;
        when(testRepository.findById(testId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> testService.getTestById(testId))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @org.junit.jupiter.api.Test
    void createTest_WithValidData_ReturnsCreatedTest() {
        // Arrange
        Test inputTest = new Test();
        when(testRepository.save(any())).thenReturn(inputTest);

        // Act
        Test result = testService.createTest(inputTest);

        // Assert
        assertThat(result).isSameAs(inputTest);
    }

    @org.junit.jupiter.api.Test
    void updateTest_WithExistingTest_ReturnsUpdatedTest() {
        // Arrange
        Long testId = 1L;
        Test updatedTest = new Test();
        updatedTest.setTitle("Updated Test");
        Optional<Test> existingTest = Optional.of(new Test());
        when(testRepository.findById(testId)).thenReturn(existingTest);
        when(testRepository.save(any())).thenReturn(updatedTest);

        // Act
        Test result = testService.updateTest(testId, updatedTest);

        // Assert
        assertThat(result).isSameAs(updatedTest);
    }

    @org.junit.jupiter.api.Test
    void updateTest_WithNonexistentTest_ThrowsResourceNotFoundException() {
        // Arrange
        Long testId = 1L;
        Test updatedTest = new Test();
        when(testRepository.findById(testId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> testService.updateTest(testId, updatedTest))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @org.junit.jupiter.api.Test
    void deleteTest_WithExistingTest_DeletesTest() {
        // Arrange
        Long testId = 1L;
        Test existingTest = new Test();
        when(testRepository.findById(testId)).thenReturn(Optional.of(existingTest));

        // Act
        testService.deleteTest(testId);

        // Assert
        verify(testRepository, times(1)).delete(existingTest);
    }

    @org.junit.jupiter.api.Test
    void deleteTest_WithNonexistentTest_ThrowsResourceNotFoundException() {
        // Arrange
        Long testId = 1L;
        when(testRepository.findById(testId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> testService.deleteTest(testId))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
