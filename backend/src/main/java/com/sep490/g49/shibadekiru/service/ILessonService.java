package com.sep490.g49.shibadekiru.service;

import com.sep490.g49.shibadekiru.dto.LessonDto;
import com.sep490.g49.shibadekiru.entity.Lesson;

import java.util.List;


public interface ILessonService {

    List<LessonDto> getAllLessons();

    LessonDto createLesson(LessonDto lesson);

    LessonDto updateLesson(Long lessonId, LessonDto updatedLesson);

    void deleteLesson(Long lessonId);

    LessonDto getLessonById(Long lessonId);


}
