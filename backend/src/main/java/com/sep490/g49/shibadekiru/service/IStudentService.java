package com.sep490.g49.shibadekiru.service;

import com.sep490.g49.shibadekiru.entity.Student;
import com.sep490.g49.shibadekiru.entity.UserAccount;

public interface IStudentService {
    Student getByUserAccount(UserAccount userAccount);

    Student getStudentByStudentId(Long studentId);

    Student getStudentByStudentIdToGetAvatar(Long studentId);
}
