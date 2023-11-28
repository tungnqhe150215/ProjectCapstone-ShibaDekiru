package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.Lesson;
import com.sep490.g49.shibadekiru.entity.Writing;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.impl.WritingServiceImpl;
import com.sep490.g49.shibadekiru.repository.WritingRepository;
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

class WritingServiceImplTest {

    @Mock
    private WritingRepository writingRepository;

    @InjectMocks
    private WritingServiceImpl writingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getWritingPartByLesson() {
        // Mock data
        Lesson lesson = new Lesson();
        when(writingRepository.findByLesson(lesson)).thenReturn(new ArrayList<>());

        // Test
        var result = writingService.getWritingPartByLesson(lesson);

        // Assertions
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getWritingById_WhenExists() {
        // Mock data
        Long id = 1L;
        Writing writing = new Writing();
        when(writingRepository.findById(id)).thenReturn(Optional.of(writing));

        // Test
        var result = writingService.getWritingById(id);

        // Assertions
        assertNotNull(result);
    }

    @Test
    void getWritingById_WhenNotExists() {
        // Mock data
        Long id = 1L;
        when(writingRepository.findById(id)).thenReturn(Optional.empty());

        // Test
        assertThrows(ResourceNotFoundException.class, () -> writingService.getWritingById(id));
    }

    @Test
    void createWriting() {
        // Mock data
        Writing writingRequest = new Writing();
        when(writingRepository.save(any(Writing.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Test
        var createdWriting = writingService.createWriting(writingRequest);

        // Assertions
        assertNotNull(createdWriting);
    }

    @Test
    void updateWriting_WhenExists() {
        // Mock data
        Long id = 1L;
        Writing writingRequest = new Writing();
        writingRequest.setTopic("Updated topic");
        Writing existingWriting = new Writing();
        when(writingRepository.findById(id)).thenReturn(Optional.of(existingWriting));
        when(writingRepository.save(any(Writing.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Test
        var updatedWriting = writingService.updateWriting(id, writingRequest);

        // Assertions
        assertNotNull(updatedWriting);
        assertEquals("Updated topic", updatedWriting.getTopic());
    }

    @Test
    void updateWriting_WhenNotExists() {
        // Mock data
        Long id = 1L;
        Writing writingRequest = new Writing();
        when(writingRepository.findById(id)).thenReturn(Optional.empty());

        // Test
        assertThrows(ResourceNotFoundException.class, () -> writingService.updateWriting(id, writingRequest));
    }

    @Test
    void deleteWriting_WhenExists() {
        // Mock data
        Long id = 1L;
        Writing existingWriting = new Writing();
        when(writingRepository.findById(id)).thenReturn(Optional.of(existingWriting));

        // Test
        assertDoesNotThrow(() -> writingService.deleteWriting(id));
    }

    @Test
    void deleteWriting_WhenNotExists() {
        // Mock data
        Long id = 1L;
        when(writingRepository.findById(id)).thenReturn(Optional.empty());

        // Test
        assertThrows(ResourceNotFoundException.class, () -> writingService.deleteWriting(id));
    }
}
