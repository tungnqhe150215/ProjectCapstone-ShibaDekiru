package com.sep490.g49.shibadekiru.repository;

import com.sep490.g49.shibadekiru.entity.Class;
import com.sep490.g49.shibadekiru.entity.Lectures;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<Class, Long> {

    Class findByClassCode(String code);

    List<Class> findByLectureAndIsDeletedFalse(Lectures lecture);

    boolean existsByClassCode(String code);

}
