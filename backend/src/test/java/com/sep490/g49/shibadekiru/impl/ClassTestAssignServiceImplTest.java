package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.Class;
import com.sep490.g49.shibadekiru.entity.ClassTestAssign;
import com.sep490.g49.shibadekiru.entity.Test;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.impl.ClassTestAssignServiceImpl;
import com.sep490.g49.shibadekiru.repository.ClassTestAssignRepository;
import org.junit.jupiter.api.BeforeEach;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClassTestAssignServiceImplTest {

    @Mock
    private ClassTestAssignRepository classTestAssignRepository;

    @InjectMocks
    private ClassTestAssignServiceImpl classTestAssignService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @org.junit.jupiter.api.Test
    void saveClassTestAssign() {
        // Arrange
        ClassTestAssign classTestAssignRequest = new ClassTestAssign();
        int expirationMinutes = 30;

        // Mock the save method to return the same object passed to it
        when(classTestAssignRepository.save(any(ClassTestAssign.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        ClassTestAssign result = classTestAssignService.saveClassTestAssign(classTestAssignRequest, expirationMinutes);

        // Assert
        assertNotNull(result.getAccessExpirationDate());
        verify(classTestAssignRepository, times(1)).save(any(ClassTestAssign.class));
    }

    @org.junit.jupiter.api.Test
    void getAllClassTestRelationships() {
        // Arrange
        List<ClassTestAssign> classTestAssignList = new ArrayList<>();
        when(classTestAssignRepository.findAll()).thenReturn(classTestAssignList);

        // Act
        List<ClassTestAssign> result = classTestAssignService.getAllClassTestRelationships();

        // Assert
        assertEquals(classTestAssignList, result);
        verify(classTestAssignRepository, times(1)).findAll();
    }

    @org.junit.jupiter.api.Test
    void getClassTestById() {
        // Arrange
        Long testId = 1L;
        ClassTestAssign classTestAssign = new ClassTestAssign();
        when(classTestAssignRepository.findById(testId)).thenReturn(Optional.of(classTestAssign));

        // Act
        ClassTestAssign result = classTestAssignService.getClassTestById(testId);

        // Assert
        assertEquals(classTestAssign, result);
        verify(classTestAssignRepository, times(1)).findById(testId);
    }

    @org.junit.jupiter.api.Test
    void getAllClassTestByClass() {
        // Arrange
        Class assignedClass = new Class();
        List<ClassTestAssign> classTestAssignList = new ArrayList<>();
        when(classTestAssignRepository.findAllByAssignedClass(assignedClass)).thenReturn(classTestAssignList);

        // Act
        List<ClassTestAssign> result = classTestAssignService.getAllClassTestByClass(assignedClass);

        // Assert
        assertEquals(classTestAssignList, result);
        verify(classTestAssignRepository, times(1)).findAllByAssignedClass(assignedClass);
    }

    @org.junit.jupiter.api.Test
    void getAllClassTestByTest() {
        // Arrange
        Test test = new Test();
        List<ClassTestAssign> classTestAssignList = new ArrayList<>();
        when(classTestAssignRepository.findAllByTest(test)).thenReturn(classTestAssignList);

        // Act
        List<ClassTestAssign> result = classTestAssignService.getAllClassTestByTest(test);

        // Assert
        assertEquals(classTestAssignList, result);
        verify(classTestAssignRepository, times(1)).findAllByTest(test);
    }

    @org.junit.jupiter.api.Test
    void updateExpireDate() {
        // Arrange
        Long id = 1L;
        int expirationMinutes = 30;
        ClassTestAssign classTestAssign = new ClassTestAssign();
        when(classTestAssignRepository.findById(id)).thenReturn(Optional.of(classTestAssign));
        when(classTestAssignRepository.save(any(ClassTestAssign.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        ClassTestAssign result = classTestAssignService.updateExpireDate(id, expirationMinutes);

        // Assert
        assertNotNull(result.getAccessExpirationDate());
        verify(classTestAssignRepository, times(1)).findById(id);
        verify(classTestAssignRepository, times(1)).save(any(ClassTestAssign.class));
    }

    @org.junit.jupiter.api.Test
    void deleteClassTest() {
        // Arrange
        Long id = 1L;
        ClassTestAssign classTestAssign = new ClassTestAssign();
        when(classTestAssignRepository.findById(id)).thenReturn(Optional.of(classTestAssign));

        // Act
        classTestAssignService.deleteClassTest(id);

        // Assert
        verify(classTestAssignRepository, times(1)).findById(id);
        verify(classTestAssignRepository, times(1)).delete(classTestAssign);
    }
}
