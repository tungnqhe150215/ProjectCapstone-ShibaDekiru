package com.sep490.g49.shibadekiru.service;

import com.sep490.g49.shibadekiru.entity.Class;
import com.sep490.g49.shibadekiru.entity.ClassTestAssign;
import com.sep490.g49.shibadekiru.entity.Test;

import java.util.List;

public interface IClassTestAssignService {

    ClassTestAssign saveClassTestAssign(ClassTestAssign classTestAssign, int expirationMinutes);

    List<ClassTestAssign> getAllClassTestRelationships();

    ClassTestAssign getClassTestById(Long id);

    List<ClassTestAssign> getAllClassTestByClass(Class assignedClass);

    List<ClassTestAssign> getAllClassTestByTest(Test test);

    ClassTestAssign updateExpireDate(Long id, int expirationMinutes);

    void deleteClassTest(Long id);
}
