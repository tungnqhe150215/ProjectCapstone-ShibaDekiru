package com.sep490.g49.shibadekiru.service;

import com.sep490.g49.shibadekiru.dto.LessonDto;
import com.sep490.g49.shibadekiru.entity.Lesson;

import java.util.List;


public interface ILessonService {

    List<Lesson> getAllLessons();

    Lesson createLesson(Lesson lesson);

    Lesson updateLesson(Long lessonId, Lesson updatedLesson);

    void deleteLesson(Long lessonId);

    Lesson getLessonById(Long lessonId);


}
