package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.SectionType;
import com.sep490.g49.shibadekiru.entity.Test;
import com.sep490.g49.shibadekiru.entity.TestSection;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.impl.TestSectionServiceImpl;
import com.sep490.g49.shibadekiru.repository.TestSectionRepository;
import com.sep490.g49.shibadekiru.service.GoogleDriveService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TestSectionServiceImplTest {

    @Mock
    private TestSectionRepository testSectionRepository;

    @Mock
    private GoogleDriveService googleDriveService;

    @InjectMocks
    private TestSectionServiceImpl testSectionService;

    @org.junit.jupiter.api.Test
    void getTestSectionByTypeAndTest() {
        // Mock data
        SectionType sectionType = SectionType.LISTENING;
        Test test = new Test();
        when(testSectionRepository.findTestSectionsBySectionTypeAndAndTest(sectionType, test)).thenReturn(Collections.emptyList());

        // Test
        var result = testSectionService.getTestSectionByTypeAndTest(sectionType, test);

        // Assertions
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @org.junit.jupiter.api.Test
    void getTestSectionByTest() {
        // Mock data
        Test test = new Test();
        when(testSectionRepository.findTestSectionsByTest(test)).thenReturn(Collections.emptyList());

        // Test
        var result = testSectionService.getTestSectionByTest(test);

        // Assertions
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @org.junit.jupiter.api.Test
    void getTestSectionById() {
        // Mock data
        Long id = 1L;
        TestSection testSection = new TestSection();
        when(testSectionRepository.findById(id)).thenReturn(Optional.of(testSection));

        // Test
        var result = testSectionService.getTestSectionById(id);

        // Assertions
        assertNotNull(result);
        assertEquals(testSection, result);
    }

    @org.junit.jupiter.api.Test
    void createTestSection() {
        // Mock data
        TestSection testSectionRequest = new TestSection();
        when(testSectionRepository.save(any(TestSection.class))).thenReturn(testSectionRequest);

        // Test
        var createdTestSection = testSectionService.createTestSection(testSectionRequest);

        // Assertions
        assertNotNull(createdTestSection);
        assertEquals(testSectionRequest, createdTestSection);
    }

    @org.junit.jupiter.api.Test
    void updateTestSection() {
        // Mock data
        Long id = 1L;
        TestSection testSectionRequest = new TestSection();
        TestSection existingTestSection = new TestSection();
        when(testSectionRepository.findById(id)).thenReturn(Optional.of(existingTestSection));
        when(testSectionRepository.save(any(TestSection.class))).thenReturn(testSectionRequest);

        // Test
        var updatedTestSection = testSectionService.updateTestSection(id, testSectionRequest);

        // Assertions
        assertNotNull(updatedTestSection);
        assertEquals(testSectionRequest, updatedTestSection);
    }

    @org.junit.jupiter.api.Test
    void deleteTestSection() {
        // Mock data
        Long id = 1L;
        TestSection testSection = new TestSection();
        testSection.setSectionType(SectionType.LISTENING);
        when(testSectionRepository.findById(id)).thenReturn(Optional.of(testSection));

        // Test
        assertDoesNotThrow(() -> testSectionService.deleteTestSection(id));

        // Verify if googleDriveService.deleteFile() was called
        verify(googleDriveService, times(1)).deleteFile(testSection.getSectionAttach());
    }
}
