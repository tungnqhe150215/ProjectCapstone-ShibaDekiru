package com.sep490.g49.shibadekiru.repository;

import com.sep490.g49.shibadekiru.entity.Student;
import com.sep490.g49.shibadekiru.entity.StudentClassWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentClassWorkRepository extends JpaRepository<StudentClassWork, Long> {

    List<StudentClassWork> findByStudent(Student student);

}
