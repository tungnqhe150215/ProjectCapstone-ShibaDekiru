package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.Student;
import com.sep490.g49.shibadekiru.entity.UserAccount;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createStudentFromUserAccount() {
        // Mocking the behavior of studentRepository.save() method
        Student student = new Student();
        when(studentRepository.save(student)).thenReturn(student);

        // Calling the method to be tested
        studentService.createStudentFromUserAccount(student);

        // Verifying that save method was called
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void getByUserAccount() {
        // Mocking the behavior of studentRepository.findByUserAccount() method
        UserAccount userAccount = new UserAccount();
        Student expectedStudent = new Student();
        when(studentRepository.findByUserAccount(userAccount)).thenReturn(expectedStudent);

        // Calling the method to be tested
        Student result = studentService.getByUserAccount(userAccount);

        // Assertions
        assertNotNull(result);
        assertEquals(expectedStudent, result);
    }

    @Test
    void getStudentByStudentId() {
        // Mocking the behavior of studentRepository.findById() method
        Long studentId = 1L;
        Student expectedStudent = new Student();
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(expectedStudent));

        // Calling the method to be tested
        Student result = studentService.getStudentByStudentId(studentId);

        // Assertions
        assertNotNull(result);
        assertEquals(expectedStudent, result);
    }

    @Test
    void getStudentByStudentId_NotFound() {
        // Mocking the behavior of studentRepository.findById() method when student is not found
        Long studentId = 1L;
        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        // Calling the method to be tested and expecting a ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> studentService.getStudentByStudentId(studentId));
    }
}
