package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.Class;
import com.sep490.g49.shibadekiru.entity.ClassStudent;
import com.sep490.g49.shibadekiru.entity.Lectures;
import com.sep490.g49.shibadekiru.entity.Student;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.repository.ClassStudentRepository;
import com.sep490.g49.shibadekiru.service.IClassStudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClassStudentServiceImplTest {

    @Mock
    private ClassStudentRepository classStudentRepository;

    @InjectMocks
    private ClassStudentServiceImpl classStudentService;

    private ClassStudent classStudent;
    private Student student;
    private Class aClass;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        classStudent = new ClassStudent();
        student = new Student();
        aClass = new Class();
        Lectures lectures = new Lectures();

        classStudent.setClassStudentId(1L);
        classStudent.setBelongClass(aClass);
        classStudent.setStudent(student);
        classStudent.setJoinedAt(LocalDateTime.parse(LocalDateTime.now().toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        student.setStudentId(1L);
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setEmail("john.doe@example.com");

        aClass.setClassId(1L);
        aClass.setClassName("Mathematics");
        lectures.setLectureId(1L);
    }

    @Test
    void getClassStudentByClass_ShouldReturnClassStudents() {
        List<ClassStudent> classStudents = new ArrayList<>();
        classStudents.add(classStudent);

        when(classStudentRepository.findClassStudentsByBelongClass(aClass)).thenReturn(classStudents);

        List<ClassStudent> result = classStudentService.getClassStudentByClass(aClass);

        assertEquals(classStudents, result);
        assertEquals(1, result.size());

        verify(classStudentRepository, times(1)).findClassStudentsByBelongClass(aClass);
    }

    @Test
    void getClassStudentByStudent_ShouldReturnClassStudents() {
        List<ClassStudent> classStudents = new ArrayList<>();
        classStudents.add(classStudent);

        when(classStudentRepository.findByStudent(student)).thenReturn(classStudents);

        List<ClassStudent> result = classStudentService.getClassStudentByStudent(student);

        assertEquals(classStudents, result);
        assertEquals(1, result.size());

        verify(classStudentRepository, times(1)).findByStudent(student);
    }

    @Test
    void getClassStudentById_ShouldReturnClassStudent() {
        when(classStudentRepository.findById(classStudent.getClassStudentId())).thenReturn(Optional.of(classStudent));

        ClassStudent result = classStudentService.getClassStudentById(classStudent.getClassStudentId());

        assertEquals(classStudent, result);

        verify(classStudentRepository, times(1)).findById(classStudent.getClassStudentId());
    }

    @Test
    void getClassStudentById_ShouldThrowResourceNotFoundException() {
        when(classStudentRepository.findById(classStudent.getClassStudentId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            classStudentService.getClassStudentById(classStudent.getClassStudentId());
        });

        verify(classStudentRepository, times(1)).findById(classStudent.getClassStudentId());
    }

    @Test
    void createClassStudent_ShouldCreateClassStudent() {
        when(classStudentRepository.save(classStudent)).thenReturn(classStudent);

        ClassStudent result = classStudentService.createClassStudent(classStudent);

        assertEquals(classStudent, result);

        verify(classStudentRepository, times(1)).save(classStudent);
    }

    @Test
    void deleteClassStudent_ShouldDeleteClassStudent() {
        when(classStudentRepository.findById(classStudent.getClassStudentId())).thenReturn(Optional.of(classStudent));

        classStudentService.deleteClassStudent(classStudent.getClassStudentId());

        verify(classStudentRepository, times(1)).findById(classStudent.getClassStudentId());
        verify(classStudentRepository, times(1)).delete(classStudent);
    }

    @Test
    void deleteClassStudent_ShouldThrowResourceNotFoundException() {
        when(classStudentRepository.findById(classStudent.getClassStudentId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            classStudentService.deleteClassStudent(classStudent.getClassStudentId());
        });

        verify(classStudentRepository, times(1)).findById(classStudent.getClassStudentId());
    }
}