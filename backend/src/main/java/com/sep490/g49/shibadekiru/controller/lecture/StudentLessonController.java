package com.sep490.g49.shibadekiru.controller.lecture;

import com.sep490.g49.shibadekiru.dto.ClassWorkDto;
import com.sep490.g49.shibadekiru.dto.LessonDto;
import com.sep490.g49.shibadekiru.dto.ReadingDto;
import com.sep490.g49.shibadekiru.dto.WritingDto;
import com.sep490.g49.shibadekiru.entity.Book;
import com.sep490.g49.shibadekiru.entity.Lesson;
import com.sep490.g49.shibadekiru.service.IBookService;
import com.sep490.g49.shibadekiru.service.ILessonService;
import com.sep490.g49.shibadekiru.service.IReadingService;
import com.sep490.g49.shibadekiru.service.IWritingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/book")
public class StudentLessonController {

    ModelMapper modelMapper;

    IBookService bookService;

    ILessonService lessonService;

    IReadingService readingService;

    IWritingService writingService;

    @GetMapping("/{id}/lesson")
    public List<LessonDto> getAllLessonByBook(@PathVariable (name = "id") Long bookId) {
        Book bookResponse = bookService.getBookById(bookId);
        return lessonService.getLessonPartByBook(bookResponse).stream().map(lessonDto -> modelMapper.map(lessonDto, LessonDto.class)).collect(Collectors.toList());
    }

//    @GetMapping("/lesson/{lessonId}/reading")
//    public List<ReadingDto> getAllReadingByLesson(@PathVariable (name = "lessonId") Long lessonId) {
//        Lesson lessonResponse = lessonService.getLessonById(lessonId);
//
//        return readingService.getAllReadingByLesson(lessonResponse).stream().map(readingDto -> modelMapper.map(readingDto, ReadingDto.class)).collect(Collectors.toList());
//    }
//
//
//
//    @GetMapping("/lesson/{lessonId}/writing")
//    public List<WritingDto> getAllWritingByLesson(@PathVariable (name = "lessonId") Long lessonId) {
//        Lesson lessonResponse = lessonService.getLessonById(lessonId);
//
//        return writingService.getWritingPartByLesson(lessonResponse).stream().map(writingDto -> modelMapper.map(writingDto, WritingDto.class)).collect(Collectors.toList());
//    }
}
