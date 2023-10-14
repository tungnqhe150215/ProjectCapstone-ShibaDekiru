package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.dto.BookDto;
import com.sep490.g49.shibadekiru.dto.LessonDto;
import com.sep490.g49.shibadekiru.entity.Book;
import com.sep490.g49.shibadekiru.entity.Lesson;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.repository.BookRepository;
import com.sep490.g49.shibadekiru.repository.LessonRepository;
import com.sep490.g49.shibadekiru.service.ILessonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LessonServiceImpl implements ILessonService {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ModelMapper modelMapper;

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
            lessonDto.setBookId(lesson.getBook().getBookId());

            lessonDtos.add(lessonDto);
        }

        return lessonDtos;
    }

    @Override
    public LessonDto createLesson(LessonDto lessonDto) {

        LocalDateTime currentDateTime = LocalDateTime.now();


        String name = lessonDto.getName();
        String content = lessonDto.getContent();
        boolean status = lessonDto.getStatus();
        String image = lessonDto.getImage();
        Long bookId = lessonDto.getBookId();


        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isPresent()) {

            Book book = bookOptional.get();


            Lesson lesson = new Lesson();
            lesson.setName(name);
            lesson.setContent(content);
            lesson.setCreatedAt(currentDateTime);
            lesson.setStatus(status);
            lesson.setImage(image);
            lesson.setBook(book);


            Lesson createdLesson = lessonRepository.save(lesson);


            return modelMapper.map(createdLesson, LessonDto.class);
        } else {
            throw new ResourceNotFoundException("Lesson can't be added.");
        }
    }


    @Override
    public LessonDto updateLesson(Long lessonId, LessonDto updatedLesson) {

        Optional<Lesson> existingLesson = lessonRepository.findById(lessonId);
        if (existingLesson.isPresent()) {
            Lesson lesson = existingLesson.get();


            lesson.setName(updatedLesson.getName());
            lesson.setContent(updatedLesson.getContent());
            lesson.setStatus(updatedLesson.getStatus());
            lesson.setImage(updatedLesson.getImage());


            Lesson updated = lessonRepository.save(lesson);


            return modelMapper.map(updated, LessonDto.class);
        } else {
            throw new ResourceNotFoundException("Lesson not found");
        }
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
            return null;
        }

        LessonDto lessonDto = new LessonDto();
        lessonDto.setLessonId(lesson.getLessonId());
        lessonDto.setName(lesson.getName());
        lessonDto.setContent(lesson.getContent());
        lessonDto.setCreatedAt(lesson.getCreatedAt());
        lessonDto.setStatus(lesson.getStatus());
        lessonDto.setImage(lesson.getImage());
        lessonDto.setBookId(lesson.getBook().getBookId());


        return lessonDto;
    }
}
