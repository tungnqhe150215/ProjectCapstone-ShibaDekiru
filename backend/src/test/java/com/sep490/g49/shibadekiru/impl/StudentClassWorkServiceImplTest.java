package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.*;
import com.sep490.g49.shibadekiru.entity.Class;
import com.sep490.g49.shibadekiru.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentClassWorkServiceImplTest {

    @Mock
    private StudentClassWorkRepository studentClassWorkRepository;

    @Mock
    private ClassWorkRepository classWorkRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private ClassRepository classRepository;

    @InjectMocks
    private StudentClassWorkServiceImpl studentClassWorkService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getStudentClassWorkByStudent() {
        // Mocking the behavior of studentClassWorkRepository.findByStudent() method
        Student student = new Student();
        List<StudentClassWork> expectedStudentClassWorks = new ArrayList<>();
        when(studentClassWorkRepository.findByStudent(student)).thenReturn(expectedStudentClassWorks);

        // Calling the method to be tested
        List<StudentClassWork> result = studentClassWorkService.getStudentClassWorkByStudent(student);

        // Assertions
        assertNotNull(result);
        assertEquals(expectedStudentClassWorks, result);
    }

    @Test
    void getStudentClassWorkByClassWork() {
        // Mocking the behavior of studentClassWorkRepository.findByClassWork() method
        ClassWork classWork = new ClassWork();
        List<StudentClassWork> expectedStudentClassWorks = new ArrayList<>();
        when(studentClassWorkRepository.findByClassWork(classWork)).thenReturn(expectedStudentClassWorks);

        // Calling the method to be tested
        List<StudentClassWork> result = studentClassWorkService.getStudentClassWorkByClassWork(classWork);

        // Assertions
        assertNotNull(result);
        assertEquals(expectedStudentClassWorks, result);
    }

    @Test
    void createStudentClassWork() {
        // Mocking the behavior of studentClassWorkRepository.save() method
        StudentClassWork studentClassWorkRequest = new StudentClassWork();
        studentClassWorkRequest.setSubmitTime(LocalDateTime.now());
        when(studentClassWorkRepository.save(studentClassWorkRequest)).thenReturn(studentClassWorkRequest);

        // Calling the method to be tested
        StudentClassWork result = studentClassWorkService.createStudentClassWork(studentClassWorkRequest);

        // Assertions
        assertNotNull(result);
        assertEquals(studentClassWorkRequest.getSubmitTime(), result.getSubmitTime());
    }

    @Test
    void updateStudentClassWork() {
        // Mocking the behavior of studentClassWorkRepository.findByStudentAndClassWork() and studentClassWorkRepository.save() methods
        StudentClassWork studentClassWorkRequest = new StudentClassWork();
        Student student = new Student();
        ClassWork classWork = new ClassWork();
        studentClassWorkRequest.setStudent(student);
        studentClassWorkRequest.setClassWork(classWork);

        StudentClassWork existingStudentClassWork = new StudentClassWork();
        when(studentClassWorkRepository.findByStudentAndClassWork(student, classWork)).thenReturn(existingStudentClassWork);
        when(studentClassWorkRepository.save(existingStudentClassWork)).thenReturn(existingStudentClassWork);

        // Calling the method to be tested
        StudentClassWork result = studentClassWorkService.updateStudentClassWork(studentClassWorkRequest);

        // Assertions
        assertNotNull(result);
        assertEquals(existingStudentClassWork.getSubmitTime(), result.getSubmitTime());
        assertEquals(existingStudentClassWork.getResult(), result.getResult());
    }

    @Test
    void deleteStudentClassWork() {
        // Mocking the behavior of studentClassWorkRepository.findById() and studentClassWorkRepository.delete() methods
        Long id = 1L;
        StudentClassWork existingStudentClassWork = new StudentClassWork();
        when(studentClassWorkRepository.findById(id)).thenReturn(java.util.Optional.of(existingStudentClassWork));

        // Calling the method to be tested
        studentClassWorkService.deleteStudentClassWork(id);

        // Verifying that delete method was called
        verify(studentClassWorkRepository, times(1)).delete(existingStudentClassWork);
    }

    @Test
    void getStudentClassWorkByClassWorkAndStudent() {
        // Mocking the behavior of studentRepository.findById(), classWorkRepository.findById(), and studentClassWorkRepository.findByStudentAndClassWork() methods
        Long classworkId = 1L;
        Long studentId = 2L;

        Student student = new Student();
        ClassWork classWork = new ClassWork();
        StudentClassWork expectedStudentClassWork = new StudentClassWork();

        when(studentRepository.findById(studentId)).thenReturn(java.util.Optional.of(student));
        when(classWorkRepository.findById(classworkId)).thenReturn(java.util.Optional.of(classWork));
        when(studentClassWorkRepository.findByStudentAndClassWork(student, classWork)).thenReturn(expectedStudentClassWork);

        // Calling the method to be tested
        StudentClassWork result = studentClassWorkService.getStudentClassWorkByClassWorkAndStudent(classworkId, studentId);

        // Assertions
        assertNotNull(result);
        assertEquals(expectedStudentClassWork, result);
    }

//    @Test
//    void getStudentClassWorkByClassAndStudent() {
//        // Mocking the behavior of classRepository.findById(), studentRepository.findById(), classWorkRepository.findByMyC(), and studentClassWorkRepository.findByStudentAndClassWork() methods
//        Long classId = 1L;
//        Long studentId = 2L;
//
//        Class aClass = new Class();
//        Student student = new Student();
//        ClassWork classWork = new ClassWork();
//        List<ClassWork> classWorks = new ArrayList<>();
//        classWorks.add(classWork);
//        StudentClassWork expectedStudentClassWorks = new StudentClassWork();
//
//        when(classRepository.findById(classId)).thenReturn(java.util.Optional.of(aClass));
//        when(studentRepository.findById(studentId)).thenReturn(java.util.Optional.of(student));
//        when(classWorkRepository.findByMyC(aClass)).thenReturn(classWorks);
//        when(studentClassWorkRepository.findByStudentAndClassWork(student, classWork)).thenReturn(expectedStudentClassWorks);
//
//        // Calling the method to be tested
//        List<StudentClassWork> result = studentClassWorkService.getStudentClassWorkByClassAndStudent(classId, studentId);
//
//        // Assertions
//        assertNotNull(result);
//        assertEquals(expectedStudentClassWorks, result);
//    }

    @Test
    void checkStudentClassWorkExist() {
        // Mocking the behavior of studentClassWorkRepository.findByStudentAndClassWork() method
        Student student = new Student();
        ClassWork classWork = new ClassWork();

        when(studentClassWorkRepository.findByStudentAndClassWork(student, classWork)).thenReturn(new StudentClassWork());

        // Calling the method to be tested
        boolean result = studentClassWorkService.checkStudentClassWorkExist(student, classWork);

        // Assertions
        assertTrue(result);
    }
}
