package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.Class;
import com.sep490.g49.shibadekiru.entity.ClassTestAssign;
import com.sep490.g49.shibadekiru.entity.Test;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.repository.ClassTestAssignRepository;
import com.sep490.g49.shibadekiru.service.IClassTestAssignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClassTestAssignServiceImpl implements IClassTestAssignService {

    @Autowired
    private ClassTestAssignRepository classTestAssignRepository;

    @Override
    public ClassTestAssign saveClassTestAssign(ClassTestAssign classTestAssign,int expirationMinutes) {
        classTestAssign.setAccessExpirationDate(LocalDateTime.now().plusMinutes(expirationMinutes));
        return classTestAssignRepository.save(classTestAssign);
    }

    @Override
    public List<ClassTestAssign> getAllClassTestRelationships() {
        return classTestAssignRepository.findAll();
    }

    @Override
    public ClassTestAssign getClassTestById(Long id) {
        ClassTestAssign classTestAssign = classTestAssignRepository.findById(id).
                orElseThrow(null);
        return classTestAssign;
    }

    @Override
    public List<ClassTestAssign> getAllClassTestByClass(Class assignedClass) {
        return classTestAssignRepository.findAllByAssignedClass(assignedClass);
    }

    @Override
    public List<ClassTestAssign> getAllClassTestByTest(Test test){
        return classTestAssignRepository.findAllByTest(test);
    }

    @Override
    public ClassTestAssign updateExpireDate(Long id, int expirationMinutes){
        ClassTestAssign classTestAssign = classTestAssignRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Not found data"));
        classTestAssign.setAccessExpirationDate(LocalDateTime.now().plusMinutes(expirationMinutes));
        return classTestAssignRepository.save(classTestAssign);
    }

    @Override
    public void deleteClassTest(Long id){
        ClassTestAssign classTestAssign = classTestAssignRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Not found data"));
        classTestAssignRepository.delete(classTestAssign);
    }
}
