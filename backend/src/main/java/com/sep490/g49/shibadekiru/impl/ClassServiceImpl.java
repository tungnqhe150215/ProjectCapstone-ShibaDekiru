package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.Class;
import com.sep490.g49.shibadekiru.entity.Lectures;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.repository.ClassRepository;
import com.sep490.g49.shibadekiru.repository.LecturersRepository;
import com.sep490.g49.shibadekiru.service.IClassService;
import com.sep490.g49.shibadekiru.util.RandomStringGeneratorService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClassServiceImpl implements IClassService {

    @Autowired
    ClassRepository classRepository;

    @Autowired
    LecturersRepository lecturersRepository;

    @Autowired
    RandomStringGeneratorService randomStringGeneratorService;

    @Override
    public List<Class> getAllClassByLecture(Lectures lecture) {
        return classRepository.findByLectureAndIsDeletedFalse(lecture);
    }

    @Override
    public Class getClassById(Long classId) {
        Class aclass =  classRepository.findById(classId).orElse(null);

        if (aclass == null) {
            throw new ResourceNotFoundException("Class not found with: " + classId);
        }
        return aclass;
    }

    @Override
    public Class getClassByCode(String code) {
        Class aclass =  classRepository.findByClassCode(code);

        if (aclass == null) {
            throw new ResourceNotFoundException("Class not found with code: " + code);
        }
        return aclass;
    }

    @Override
    public Class createClass(Class classRequest) {
        String code;
        do {
            code = randomStringGeneratorService.randomAlphaNumeric(7);
        } while (classRepository.existsByClassCode(code));
        classRequest.setClassCode(code);
        return classRepository.save(classRequest);
    }

    @Override
    public Class updateClass(Long id, Class classRequest) {
        Class aClass = classRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found data"));
        aClass.setClassName(classRequest.getClassName());
        aClass.setIsLocked(classRequest.getIsLocked());
        //ko cần set lại id của Lecture
        return classRepository.save(aClass);
    }

    @Override
    public void deleteClass(Long id) {
        Class aClass = classRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found data"));
        classRepository.delete(aClass);
    }

    @Override
    public List<Class> getAllClass() {
        return classRepository.findAll();
    }

    @Override
    public void updateIsLocked(Long classId) {
        Optional<Class> existingClassWork = classRepository.findById(classId);

        if (existingClassWork.isPresent()) {
            Class classWork = existingClassWork.get();
            Boolean currentIsLocked = classWork.getIsLocked();

            classWork.setIsLocked(!currentIsLocked);

            classRepository.save(classWork);

        }
        else {
            throw new ResourceNotFoundException("Class not found with: " + classId);
        }
    }
}
