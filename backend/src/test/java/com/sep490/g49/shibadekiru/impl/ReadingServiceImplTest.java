package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.Lesson;
import com.sep490.g49.shibadekiru.entity.Reading;
import com.sep490.g49.shibadekiru.repository.ReadingRepository;
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

class ReadingServiceImplTest {

    @Mock
    private ReadingRepository readingRepository;

    @InjectMocks
    private ReadingServiceImpl readingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getReadingPartByLesson() {
        // Mocking the behavior of readingRepository.findReadingsByLesson() method
        Lesson lesson = new Lesson();
        List<Reading> mockReadings = new ArrayList<>();
        when(readingRepository.findReadingsByLesson(lesson)).thenReturn(mockReadings);

        List<Reading> result = readingService.getReadingPartByLesson(lesson);

        assertNotNull(result);
        assertEquals(0, result.size()); // Assuming the mockReadings list is empty
    }

    @Test
    void getReadingById() {
        // Mocking the behavior of readingRepository.findReadingByReadingId() method
        Long readingId = 1L;
        Reading expectedReading = new Reading();
        when(readingRepository.findReadingByReadingId(readingId)).thenReturn(expectedReading);

        Reading result = readingService.getReadingById(readingId);

        assertNotNull(result);
        // Add more assertions as needed
    }

    @Test
    void createReading() {
        // Mocking the behavior of readingRepository.save() method
        Reading savedReading = new Reading();
        when(readingRepository.save(any(Reading.class))).thenReturn(savedReading);

        Reading result = readingService.createReading(new Reading());

        assertNotNull(result);
        // Add more assertions as needed
    }

    @Test
    void updateReading() {
        Long readingId = 1L;
        Reading updatedReading = new Reading();

        // Mocking the behavior of readingRepository.findReadingByReadingId() method
        Reading existingReading = new Reading();
        when(readingRepository.findReadingByReadingId(readingId)).thenReturn(existingReading);

        // Mocking the behavior of readingRepository.save() method
        Reading savedReading = new Reading();
        when(readingRepository.save(any(Reading.class))).thenReturn(savedReading);

        Reading result = readingService.updateReading(readingId, updatedReading);

        assertNotNull(result);
        // Add more assertions as needed
    }

    @Test
    void deleteReading() {
        Long readingId = 1L;

        // Mocking the behavior of readingRepository.findReadingByReadingId() method
        Reading existingReading = new Reading();
        when(readingRepository.findReadingByReadingId(readingId)).thenReturn(existingReading);

        assertDoesNotThrow(() -> readingService.deleteReading(readingId));
    }

    @Test
    void getAllReadingByLesson() {
        // Mocking the behavior of readingRepository.findReadingByLesson() method
        Lesson lesson = new Lesson();
        List<Reading> mockReadings = new ArrayList<>();
        when(readingRepository.findReadingByLesson(lesson)).thenReturn(mockReadings);

        List<Reading> result = readingService.getAllReadingByLesson(lesson);

        assertNotNull(result);
        assertEquals(0, result.size()); // Assuming the mockReadings list is empty
    }
}
