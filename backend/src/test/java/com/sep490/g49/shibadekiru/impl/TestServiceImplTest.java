package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.Lectures;
import com.sep490.g49.shibadekiru.entity.Test;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.impl.TestServiceImpl;
import com.sep490.g49.shibadekiru.repository.LecturersRepository;
import com.sep490.g49.shibadekiru.repository.TestRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TestServiceImplTest {

    @Mock
    private LecturersRepository lecturersRepository;

    @Mock
    private TestRepository testRepository;

    @InjectMocks
    private TestServiceImpl testService;

    @org.junit.jupiter.api.Test
    void getAllTestByLecture() {
        // Mock data
        Lectures lecture = new Lectures();
        when(testRepository.findAllByLecture(lecture)).thenReturn(Collections.emptyList());

        // Test
        var result = testService.getAllTestByLecture(lecture);

        // Assertions
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @org.junit.jupiter.api.Test
    void getTestById() {
        // Mock data
        Long testId = 1L;
        Test test = new Test();
        when(testRepository.findById(testId)).thenReturn(Optional.of(test));

        // Test
        var result = testService.getTestById(testId);

        // Assertions
        assertNotNull(result);
        assertEquals(test, result);
    }

    @org.junit.jupiter.api.Test
    void createTest() {
        // Mock data
        LocalDateTime currentDateTime = LocalDateTime.now();
        String title = "Test Title";
        Long duration = 60L;
        Boolean isLocked = false;
        Lectures lecture = new Lectures();

        Test testRequest = new Test();
        testRequest.setTitle(title);
        testRequest.setDuration(duration);
        testRequest.setIsLocked(isLocked);
        testRequest.setLecture(lecture);

        when(testRepository.save(any(Test.class))).thenReturn(testRequest);

        // Test
        var createdTest = testService.createTest(testRequest);

        // Assertions
        assertNotNull(createdTest);
        assertEquals(title, createdTest.getTitle());
        assertEquals(duration, createdTest.getDuration());
        assertEquals(isLocked, createdTest.getIsLocked());
        assertEquals(lecture, createdTest.getLecture());
        assertNotNull(createdTest.getCreatedAt());
    }

    @org.junit.jupiter.api.Test
    void updateTest() {
        // Mock data
        Long testId = 1L;
        String updatedTitle = "Updated Title";
        Long updatedDuration = 90L;
        Boolean updatedIsLocked = true;

        Test testUpdate = new Test();
        testUpdate.setTitle(updatedTitle);
        testUpdate.setDuration(updatedDuration);
        testUpdate.setIsLocked(updatedIsLocked);

        Test existingTest = new Test();
        existingTest.setTestId(testId);

        when(testRepository.findById(testId)).thenReturn(Optional.of(existingTest));
        when(testRepository.save(any(Test.class))).thenReturn(testUpdate);

        // Test
        var updatedTest = testService.updateTest(testId, testUpdate);

        // Assertions
        assertNotNull(updatedTest);
        assertEquals(testId, updatedTest.getTestId());
        assertEquals(updatedTitle, updatedTest.getTitle());
        assertEquals(updatedDuration, updatedTest.getDuration());
        assertEquals(updatedIsLocked, updatedTest.getIsLocked());
    }

    @org.junit.jupiter.api.Test
    void deleteTest() {
        // Mock data
        Long testId = 1L;
        Test testDelete = new Test();
        when(testRepository.findById(testId)).thenReturn(Optional.of(testDelete));

        // Test
        assertDoesNotThrow(() -> testService.deleteTest(testId));
    }
}
