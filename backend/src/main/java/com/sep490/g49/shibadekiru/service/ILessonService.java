package com.sep490.g49.shibadekiru.service;

import com.sep490.g49.shibadekiru.dto.LessonDto;
import com.sep490.g49.shibadekiru.entity.Lesson;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ILessonService {

    List<LessonDto> getAllLessons();

    Lesson createLesson(Lesson lesson);

    Lesson updateLesson(Long lessonId, Lesson lesson);

    void deleteLesson(Long lessonId);

    LessonDto getLessonById(Long lessonId);


}
