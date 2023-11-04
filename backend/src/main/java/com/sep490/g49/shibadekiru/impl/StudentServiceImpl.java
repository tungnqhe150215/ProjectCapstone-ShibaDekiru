package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.service.IStudentService;

public class StudentServiceImpl implements IStudentService {

    @Autowired
    private StudentRepository studentRepository;

    public void createStudentFromUserAccount(Student student){

            student.setGender(false);
            student.setAvatar("");
            student.setUserAccount(student.getUserAccount());

            studentRepository.save(student);
        }


}
