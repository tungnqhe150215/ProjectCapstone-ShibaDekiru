package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.Class;
import com.sep490.g49.shibadekiru.entity.Lectures;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.impl.ClassServiceImpl;
import com.sep490.g49.shibadekiru.repository.ClassRepository;
import com.sep490.g49.shibadekiru.repository.LecturersRepository;
import com.sep490.g49.shibadekiru.service.IClassService;
import com.sep490.g49.shibadekiru.util.RandomStringGeneratorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class ClassServiceImplTest {

    @Mock
    private ClassRepository classRepository;

    @Mock
    private LecturersRepository lecturersRepository;

    @Mock
    private RandomStringGeneratorService randomStringGeneratorService;

    @InjectMocks
    private ClassServiceImpl classService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllClassByLecture() {
        // Arrange
        Lectures lecture = new Lectures();
        List<Class> expectedClasses = new ArrayList<>();
        expectedClasses.add(new Class());
        expectedClasses.add(new Class());
        when(classRepository.findByLecture(any())).thenReturn(expectedClasses);

        // Act
        List<Class> actualClasses = classService.getAllClassByLecture(lecture);

        // Assert
        assertEquals(expectedClasses, actualClasses);
        assertEquals(expectedClasses.size(), actualClasses.size());

    }

    @Test
    void getClassById() {
        // Arrange
        Long classId = 1L;
        Class expectedClass = new Class();
        when(classRepository.findById(classId)).thenReturn(Optional.of(expectedClass));

        // Act
        Class actualClass = classService.getClassById(classId);

        // Assert
        assertEquals(expectedClass, actualClass);
    }

    @Test
    void getClassById_ThrowsResourceNotFoundException() {
        // Arrange
        Long invalidClassId = 2L;
        when(classRepository.findById(invalidClassId)).thenReturn(Optional.empty());

        // Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            // Act
            classService.getClassById(invalidClassId);
        });
    }

    @Test
    void createClass() {
        // Arrange
        Class classRequest = new Class();
        Class expectedClass = new Class();
        when(randomStringGeneratorService.randomAlphaNumeric(7)).thenReturn("ABC123");
        when(classRepository.save(classRequest)).thenReturn(expectedClass);

        // Act
        Class actualClass = classService.createClass(classRequest);

        // Assert
        assertEquals(expectedClass, actualClass);
        assertEquals("ABC123", actualClass.getClassCode());
    }

    @Test
    void updateClass() {
        // Arrange
        Long classId = 1L;
        Class classRequest = new Class();
        classRequest.setClassName("New Class Name");
        classRequest.setIsLocked(true);

        Class existingClass = new Class();
        existingClass.setClassName("Old Class Name");
        existingClass.setIsLocked(false);

        when(classRepository.findById(classId)).thenReturn(Optional.of(existingClass));
        when(classRepository.save(existingClass)).thenReturn(existingClass);

        // Act
        Class updatedClass = classService.updateClass(classId, classRequest);

        // Assert
        assertEquals(classRequest.getClassName(), updatedClass.getClassName());
        assertEquals(classRequest.getIsLocked(), updatedClass.getIsLocked());
    }

    @Test
    void updateClass_ThrowsResourceNotFoundException() {
        // Arrange
        Long invalidClassId = 2L;
        Class classRequest = new Class();
        when(classRepository.findById(invalidClassId)).thenReturn(Optional.empty());

        // Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            // Act
            classService.updateClass(invalidClassId, classRequest);
        });
    }

    @Test
    void deleteClass() {
        // Arrange
        Long classId = 1L;
        Class existingClass = new Class();

        when(classRepository.findById(classId)).thenReturn(Optional.of(existingClass));

        // Act
        classService.deleteClass(classId);

        // Assert
        // No exception should be thrown
    }

    @Test
    void deleteClass_ThrowsResourceNotFoundException() {
        // Arrange
        Long invalidClassId = 2L;
        when(classRepository.findById(invalidClassId)).thenReturn(Optional.empty());

        // Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            // Act
            classService.deleteClass(invalidClassId);
        });
    }

    @Test
    void getAllClass() {
        // Arrange
        List<Class> expectedClasses = new ArrayList<>();
        expectedClasses.add(new Class());
        expectedClasses.add(new Class());
        when(classRepository.findAll()).thenReturn(expectedClasses);

        // Act
        List<Class> actualClasses = classService.getAllClass();

        // Assert
        assertEquals(expectedClasses, actualClasses);
        assertEquals(expectedClasses.size(), actualClasses.size());
    }

    @Test
    void updateIsLocked() {
        // Arrange
        Long classId = 1L;
        Class existingClass = new Class();
        existingClass.setIsLocked(false);
        when(classRepository.findById(classId)).thenReturn(Optional.of(existingClass));
        when(classRepository.save(existingClass)).thenReturn(existingClass);

        // Act
        classService.updateIsLocked(classId);

        // Assert
        assertTrue(existingClass.getIsLocked());
    }

    @Test
    void updateIsLocked_ThrowsResourceNotFoundException() {
        // Arrange
        Long invalidClassId = 2L;
        when(classRepository.findById(invalidClassId)).thenReturn(Optional.empty());

        // Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            // Act
            classService.updateIsLocked(invalidClassId);
        });
    }
}