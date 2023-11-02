package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.Class;
import com.sep490.g49.shibadekiru.entity.ClassStudent;
import com.sep490.g49.shibadekiru.entity.Student;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.repository.ClassStudentRepository;
import com.sep490.g49.shibadekiru.service.IClassStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ClassStudentServiceImpl implements IClassStudentService {

    @Autowired
    private ClassStudentRepository classStudentRepository;

    @Override
    public List<ClassStudent> getClassStudentByClass(Class aClass) {
        return classStudentRepository.findClassStudentsByBelongClass(aClass);
    }

    @Override
    public List<ClassStudent> getClassStudentByStudent(Student student) {
        return classStudentRepository.findByStudent(student);
    }

    @Override
    public ClassStudent getClassStudentById(Long id) {
        ClassStudent classStudent = classStudentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found data"));
        return classStudent;
    }

    @Override
    public ClassStudent createClassStudent(ClassStudent classStudentRequest) {
        classStudentRequest.setJoinedAt(LocalDateTime.parse(LocalDateTime.now().toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return classStudentRepository.save(classStudentRequest);
    }

    @Override
    public void deleteClassStudent(Long id) {
        ClassStudent classStudent = classStudentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found data"));
        classStudentRepository.delete(classStudent);
    }
}
