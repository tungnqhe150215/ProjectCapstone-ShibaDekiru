package com.sep490.g49.shibadekiru.service;

import com.sep490.g49.shibadekiru.entity.ClassWork;
import com.sep490.g49.shibadekiru.entity.Student;
import com.sep490.g49.shibadekiru.entity.StudentClassWork;

import java.util.List;

public interface IStudentClassWorkService {
    List<StudentClassWork> getStudentClassWorkByStudent(Student student);

    List<StudentClassWork> getStudentClassWorkByClassWork(ClassWork classWork);

    StudentClassWork createStudentClassWork(StudentClassWork studentClassWorkRequest);

    StudentClassWork updateStudentClassWork(StudentClassWork studentClassWorkRequest);

    void deleteStudentClassWork(Long id);

    StudentClassWork getStudentClassWorkByClassWorkAndStudent(Long classworkId, Long studentId);

    boolean checkStudentClassWorkExist(Student student, ClassWork classWork);
}
