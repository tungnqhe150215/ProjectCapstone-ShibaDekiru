package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.Student;
import com.sep490.g49.shibadekiru.entity.UserAccount;
import com.sep490.g49.shibadekiru.repository.StudentRepository;
import com.sep490.g49.shibadekiru.repository.UserAccountRepository;
import com.sep490.g49.shibadekiru.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentServiceImpl implements IStudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    public void createStudentFromUserAccount(UserAccount userAccount){

        String memberId = userAccount.getMemberId();

        Optional<UserAccount> userAccountOptional = userAccountRepository.findByMemberId(memberId);
        if (userAccountOptional.isPresent()) {

            UserAccount userAccount1 = userAccountOptional.get();

            Student student = new Student();
            student.setFirstName(userAccount1.getNickName());
            student.setLastName(userAccount1.getNickName());
            student.setEmail(userAccount1.getEmail());
            student.setGender(false);
            student.setAvatar("");
            student.setUserAccount(userAccount1);

            studentRepository.save(student);
        }

    }
}
