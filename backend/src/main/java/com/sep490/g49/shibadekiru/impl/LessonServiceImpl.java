package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.dto.BookDto;
import com.sep490.g49.shibadekiru.dto.LessonDto;
import com.sep490.g49.shibadekiru.entity.Lesson;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.repository.LessonRepository;
import com.sep490.g49.shibadekiru.service.ILessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LessonServiceImpl implements ILessonService {

    @Autowired
    private LessonRepository lessonRepository;


    @Override
    public List<LessonDto> getAllLessons() {
        List<Lesson> lessons = lessonRepository.findAll();

        List<LessonDto> lessonDtos = new ArrayList<>();
        for (Lesson lesson : lessons) {
            LessonDto lessonDto = new LessonDto();
            lessonDto.setLessonId(lesson.getLessonId());
            lessonDto.setName(lesson.getName());
            lessonDto.setContent(lesson.getContent());
            lessonDto.setCreatedAt(lesson.getCreatedAt());
            lessonDto.setStatus(lesson.getStatus());
            lessonDto.setImage(lesson.getImage());

            // Tạo một đối tượng BookDto và thiết lập thông tin
            BookDto bookDto = new BookDto();
            bookDto.setBookId(lesson.getBook().getBookId());
            bookDto.setName(lesson.getBook().getName());
            bookDto.setDescription(lesson.getBook().getDescription());
            bookDto.setImage(lesson.getBook().getImage());
            lessonDto.setBookDto(bookDto);

            lessonDtos.add(lessonDto);
        }

        return lessonDtos;
    }

    @Override
    public Lesson createLesson(Lesson lesson) {


        return lessonRepository.save(lesson);
    }

    @Override
    public Lesson updateLesson(Long lessonId, Lesson lessonRequest) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new ResourceNotFoundException("Lesson not exist with id:" + lessonId));
        lesson.setName(lessonRequest.getName());
        lesson.setContent(lessonRequest.getContent());
        lesson.setCreatedAt(lessonRequest.getCreatedAt());
        lesson.setStatus(lessonRequest.getStatus());
        BookDto bookDto = new BookDto();
        bookDto.setBookId(lesson.getBook().getBookId());

        lesson.setBook(lessonRequest.getBook());
        return lessonRepository.save(lesson);
    }

    @Override
    public void deleteLesson(Long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new ResourceNotFoundException("Lesson not exist with id:" + lessonId));
        lessonRepository.delete(lesson);
    }

    @Override
    public LessonDto getLessonById(Long lessonId) {

        Lesson lesson = lessonRepository.findById(lessonId).orElse(null);

        if (lesson == null) {
            // Xử lý khi không tìm thấy Lesson
            return null;
        }

        LessonDto lessonDto = new LessonDto();
        lessonDto.setLessonId(lesson.getLessonId());
        lessonDto.setName(lesson.getName());
        lessonDto.setContent(lesson.getContent());
        lessonDto.setCreatedAt(lesson.getCreatedAt());
        lessonDto.setStatus(lesson.getStatus());
        lessonDto.setImage(lesson.getImage());

        // Lấy ID của Book từ đối tượng Book

        BookDto bookDto = new BookDto();
        bookDto.setBookId(lesson.getBook().getBookId());
        bookDto.setName(lesson.getBook().getName());
        bookDto.setDescription(lesson.getBook().getDescription());
        bookDto.setImage(lesson.getBook().getImage());
        lessonDto.setBookDto(bookDto);


        return lessonDto;
    }
}
