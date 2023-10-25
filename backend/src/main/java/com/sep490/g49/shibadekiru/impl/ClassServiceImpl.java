package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.Class;
import com.sep490.g49.shibadekiru.entity.ClassWork;
import com.sep490.g49.shibadekiru.entity.Lectures;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.repository.ClassRepository;
import com.sep490.g49.shibadekiru.repository.LecturersRepository;
import com.sep490.g49.shibadekiru.service.IClassService;
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

    ClassRepository classRepository;

    LecturersRepository lecturersRepository;

    @Override
    public List<Class> getAllClass() {
        return classRepository.findAll();
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
    public Class createClass(Class classs) {

        String className = classs.getClassName();
        String classCode = classs.getClassCode();
        Boolean isLocked = classs.getIsLocked();
        Long lectureId = classs.getLecture().getLectureId();

        Optional<Lectures> lecturesOptional = lecturersRepository.findById(lectureId);

        if(lecturesOptional.isPresent()) {

            Lectures lectures = lecturesOptional.get();

            Class class1 = new Class();
            class1.setClassName(className);
            class1.setClassCode(classCode);
            class1.setIsLocked(isLocked);
            class1.setLecture(lectures);

            return classRepository.save(class1);
        } else {
            throw new ResourceNotFoundException("Class can't be added.");
        }
    }

    @Override
    public Class updateClass(Long classId, Class classUpdate) {

        String className = classUpdate.getClassName();
        String classCode = classUpdate.getClassCode();
        Boolean isLocked = classUpdate.getIsLocked();
        Lectures lecture = classUpdate.getLecture();

        Optional<Class> classOptional = classRepository.findById(classId);

        if(classOptional.isPresent()) {
            Class classs = classOptional.get();

            classs.setClassName(className);
            classs.setClassCode(classCode);
            classs.setIsLocked(isLocked);
            classs.setLecture(lecture);

            return classRepository.save(classs);
        } else {
            throw new ResourceNotFoundException("Class not found");
        }
    }

    @Override
    public void deleteClass(Long classId) {
        Class classDelete = classRepository.findById(classId).orElseThrow(() -> new ResourceNotFoundException("Class not exist with id:" + classId));
        classRepository.delete(classDelete);
    }

}
