package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.*;
import com.sep490.g49.shibadekiru.entity.Class;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.repository.*;
import com.sep490.g49.shibadekiru.service.IStudentClassWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentClassWorkServiceImpl implements IStudentClassWorkService {

    @Autowired
    private StudentClassWorkRepository studentClassWorkRepository;

    @Autowired
    private ClassWorkRepository classWorkRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ClassRepository classRepository;

    @Override
    public List<StudentClassWork> getStudentClassWorkByStudent(Student student) {
       List<StudentClassWork> studentClassWorks = studentClassWorkRepository.findByStudent(student);
        return studentClassWorks;
    }

    @Override
    public List<StudentClassWork> getStudentClassWorkByClassWork(ClassWork classWork) {
        List<StudentClassWork> studentClassWorks = studentClassWorkRepository.findByClassWork(classWork);
        return studentClassWorks;
    }

    @Override
    public StudentClassWork createStudentClassWork(StudentClassWork studentClassWorkRequest) {
        studentClassWorkRequest.setSubmitTime(LocalDateTime.now());
        studentClassWorkRequest.setIsGraded(false);
        return studentClassWorkRepository.save(studentClassWorkRequest);
    }

    @Override
    public StudentClassWork updateStudentClassWork(StudentClassWork studentClassWorkRequest) {
        StudentClassWork studentClassWork = studentClassWorkRepository.findByStudentAndClassWork(studentClassWorkRequest.getStudent(),studentClassWorkRequest.getClassWork());
        studentClassWork.setSubmitTime(studentClassWorkRequest.getSubmitTime());
        studentClassWork.setResult(studentClassWorkRequest.getResult());
        if (studentClassWork.getResult() != null) {
            studentClassWork.setIsGraded(true);
        }
        return studentClassWorkRepository.save(studentClassWork);
    }

    @Override
    public void deleteStudentClassWork(Long id) {
        StudentClassWork studentClassWork = studentClassWorkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found data"));
        studentClassWorkRepository.delete(studentClassWork);
    }

    @Override
    public StudentClassWork getStudentClassWorkByClassWorkAndStudent(Long classworkId, Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Not found data"));
        ClassWork classWork = classWorkRepository.findById(classworkId).orElseThrow(() -> new ResourceNotFoundException("Not found data"));
        StudentClassWork studentClassWork = studentClassWorkRepository.findByStudentAndClassWork(student,classWork);
        return studentClassWork;
    }

    @Override
    public List<StudentClassWork> getStudentClassWorkByClassAndStudent(Long classId, Long studentId) {
        Class aClass = classRepository.findById(classId).orElseThrow(null);
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Not found data"));
        List<ClassWork> classWorks = classWorkRepository.findByMyCAndIsDeletedFalse(aClass);
        List<StudentClassWork> studentClassWorks = new ArrayList<>();
        classWorks.forEach(classWork -> {
            if (studentClassWorkRepository.findByStudentAndClassWork(student,classWork) != null)
            studentClassWorks.add(studentClassWorkRepository.findByStudentAndClassWork(student,classWork));
        });
        return studentClassWorks;
    }

    @Override
    public boolean checkStudentClassWorkExist(Student student, ClassWork classWork){
        if (studentClassWorkRepository.findByStudentAndClassWork(student,classWork) != null)
            return true;
        else return false;
    }
}
