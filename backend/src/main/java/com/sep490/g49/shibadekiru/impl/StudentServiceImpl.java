package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.Student;
import com.sep490.g49.shibadekiru.entity.UserAccount;
import com.sep490.g49.shibadekiru.repository.StudentRepository;
import com.sep490.g49.shibadekiru.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements IStudentService {

    @Autowired
    private StudentRepository studentRepository;

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

}
