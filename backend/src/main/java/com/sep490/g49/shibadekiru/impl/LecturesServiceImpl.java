package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.Lectures;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.repository.LecturersRepository;
import com.sep490.g49.shibadekiru.service.ILecturesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LecturesServiceImpl implements ILecturesService {

    @Autowired
    private LecturersRepository lecturersRepository;
    @Override
    public Lectures getLectureById(Long lectureId) {
        Lectures lectures = lecturersRepository.findById(lectureId).orElse(null);
        if (lectures == null) {
            throw new ResourceNotFoundException("Lesson not found with id: " +  lectureId);
        }
        return lectures;
    }
}
