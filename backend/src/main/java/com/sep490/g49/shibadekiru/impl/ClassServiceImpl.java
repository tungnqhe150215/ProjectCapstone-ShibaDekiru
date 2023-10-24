package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.Class;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.repository.ClassRepository;
import com.sep490.g49.shibadekiru.service.IClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassServiceImpl implements IClassService {

    @Autowired
    private ClassRepository classRepository;
    @Override
    public Class getClassById(Long classId) {
        Class aclass =  classRepository.findById(classId).orElse(null);

        if (aclass == null) {
            throw new ResourceNotFoundException("Class not found with: " + classId);
        }
        return aclass;
    }
}
