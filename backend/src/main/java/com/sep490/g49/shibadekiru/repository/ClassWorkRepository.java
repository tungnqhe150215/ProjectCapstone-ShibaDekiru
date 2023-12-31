package com.sep490.g49.shibadekiru.repository;

import com.sep490.g49.shibadekiru.entity.Class;
import com.sep490.g49.shibadekiru.entity.ClassWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassWorkRepository extends JpaRepository<ClassWork, Long> {
    List<ClassWork> findByMyCAndIsDeletedFalse(Class myC);
}
