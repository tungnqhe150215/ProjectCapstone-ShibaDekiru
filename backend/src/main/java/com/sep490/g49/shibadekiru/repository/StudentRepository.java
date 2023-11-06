package com.sep490.g49.shibadekiru.repository;

import com.sep490.g49.shibadekiru.entity.Student;
import com.sep490.g49.shibadekiru.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByUserAccount(UserAccount userAccount);
}
