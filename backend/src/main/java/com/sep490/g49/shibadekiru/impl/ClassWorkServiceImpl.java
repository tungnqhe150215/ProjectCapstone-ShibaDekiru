package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.dto.ClassWorkDto;
import com.sep490.g49.shibadekiru.entity.Class;
import com.sep490.g49.shibadekiru.entity.ClassWork;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.repository.ClassRepository;
import com.sep490.g49.shibadekiru.repository.ClassWorkRepository;
import com.sep490.g49.shibadekiru.service.IClassWorkService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ClassWorkServiceImpl implements IClassWorkService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private ClassWorkRepository classWorkRepository;


    @Override
    public List<ClassWork> getClassWorkByClass(Class myC) {
        return classWorkRepository.findByMyCAndIsDeletedFalse(myC);
    }

    @Override
    public ClassWorkDto createClassWork(ClassWorkDto classWorkDto) {


        ClassWork classWork = modelMapper.map(classWorkDto, ClassWork.class);

        Class aclass = classRepository.findById(classWorkDto.getMyCId())
                .orElseThrow(() -> new ResourceNotFoundException("Class not found"));



        classWork.setCreatedAt(LocalDateTime.now());
        classWork.setName(classWorkDto.getName());
        classWork.setDeadline(classWorkDto.getDeadline());
        classWork.setIsLocked(classWork.getIsLocked());
        classWork.setIsDeleted(false);
        //classWorkDto.setClassId(aclass.getClassId());
        classWork.setMyC(aclass);


        ClassWork saveClassWork = classWorkRepository.save(classWork);

        ClassWorkDto workDto =  modelMapper.map(saveClassWork, ClassWorkDto.class);


        return workDto;

    }

    @Override
    public ClassWork updateClassWork(Long classWorkId, ClassWork classWork) {
        ClassWork classWork1 = classWorkRepository.findById(classWorkId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found data"));
        classWork1.setName(classWork.getName());
        classWork1.setCreatedAt(LocalDateTime.now());
        classWork1.setDeadline(classWork.getDeadline());
        classWork1.setIsLocked(classWork.getIsLocked());
        return classWorkRepository.save(classWork1);


    }

    @Override
    public void deleteClassWork(Long classWorkId) {
        ClassWork classWork = classWorkRepository.findById(classWorkId).orElseThrow(() -> new ResourceNotFoundException("Class work not exist with id:" + classWorkId));
        classWork.setIsDeleted(true);
        classWorkRepository.save(classWork);
    }

    @Override
    public void updateIsLocked(Long classWorkId) {
        Optional<ClassWork> existingClassWork = classWorkRepository.findById(classWorkId);

        if (existingClassWork.isPresent()) {
            ClassWork classWork = existingClassWork.get();
            Boolean currentIsLocked = classWork.getIsLocked();

            classWork.setIsLocked(!currentIsLocked);

            classWorkRepository.save(classWork);

        }
        else {
            throw new ResourceNotFoundException("Class work not found with: " + classWorkId);
        }

    }

    @Override
    public ClassWork getClassWorkById(Long classWorkId) {
        ClassWork classWork = classWorkRepository.findById(classWorkId).orElse(null);

        if (classWork == null) {
            throw new ResourceNotFoundException("Class work not found with: " + classWorkId);
        }
        return classWork;
    }
}
