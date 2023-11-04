package com.sep490.g49.shibadekiru.service;

import com.sep490.g49.shibadekiru.entity.Class;
import com.sep490.g49.shibadekiru.entity.ClassStudent;
import com.sep490.g49.shibadekiru.entity.Student;

import java.util.List;

public interface IClassStudentService {
    List<ClassStudent> getClassStudentByClass(Class aClass);

    List<ClassStudent> getClassStudentByStudent(Student student);

    ClassStudent getClassStudentById(Long id);

    ClassStudent createClassStudent(ClassStudent classStudentRequest);

    void deleteClassStudent(Long id);
}
