package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.Class;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.repository.ClassRepository;
import com.sep490.g49.shibadekiru.service.IClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<Class> getAllClass(){
        return classRepository.findAll();
    }

    @Override
    public Class createClass(Class classRequest) {
        return classRepository.save(classRequest);
    }

    @Override
    public Class updateClass(Long id, Class classRequest) {
        Class aClass = classRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found data"));
        aClass.setClassName(classRequest.getClassName());
        aClass.setIsLocked(classRequest.getIsLocked());
        return classRepository.save(aClass);
    }

    @Override
    public void deleteClass(Long id) {
        Class aClass = classRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found data"));
        classRepository.delete(aClass);
    }
}
