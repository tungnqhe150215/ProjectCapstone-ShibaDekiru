package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.Student;
import com.sep490.g49.shibadekiru.entity.UserAccount;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.repository.StudentRepository;
import com.sep490.g49.shibadekiru.service.GoogleDriveService;
import com.sep490.g49.shibadekiru.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements IStudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private GoogleDriveService googleDriveService;

    public void createStudentFromUserAccount(Student student) {

        student.setGender(false);
        student.setAvatar("");
        student.setUserAccount(student.getUserAccount());

        studentRepository.save(student);
    }

    @Override
    public Student getByUserAccount(UserAccount userAccount){
        return studentRepository.findByUserAccount(userAccount);
    }

    @Override
    public Student getStudentByStudentId(Long studentId) {
        Student student = studentRepository.findById(studentId).orElse(null);
        if (student == null) {
            throw new ResourceNotFoundException("Student can not found with id: " + studentId);
        }
        return student;
    }

    @Override
    public Student getStudentByStudentIdToGetAvatar(Long studentId) {
        Student student = studentRepository.findById(studentId).orElse(null);
        if (student == null) {
            throw new ResourceNotFoundException("Student can not be found with id: " + studentId);
        }

        if (student.getAvatar().length() > 0) {
            student.setAvatar(googleDriveService.getFileUrl(student.getAvatar()));
            System.out.println("Đây là trong student: " + student.getAvatar());
        } else {
            student.setAvatar("");
        }

        return student;
    }

}
