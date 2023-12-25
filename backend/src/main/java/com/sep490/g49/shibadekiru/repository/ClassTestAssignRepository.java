package com.sep490.g49.shibadekiru.repository;

import com.sep490.g49.shibadekiru.entity.Class;
import com.sep490.g49.shibadekiru.entity.ClassTestAssign;
import com.sep490.g49.shibadekiru.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassTestAssignRepository extends JpaRepository<ClassTestAssign,Long> {
    List<ClassTestAssign> findAllByAssignedClass(Class aClass);

    List<ClassTestAssign> findAllByTest(Test test);

    ClassTestAssign findByAssignedClassAndTest(Class aClass,Test test);
}
