package com.sep490.g49.shibadekiru.controller;

import com.sep490.g49.shibadekiru.dto.LessonDto;
import com.sep490.g49.shibadekiru.entity.Lesson;
import com.sep490.g49.shibadekiru.service.ILessonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminManageLessonController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
   private ILessonService iLessonService;

    @GetMapping("/lesson")
    public List<LessonDto> getAllLessons() {
      return iLessonService.getAllLessons().stream().map(lesson -> modelMapper.map(lesson, LessonDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/lesson/{lessonId}")
    public ResponseEntity<LessonDto> getLessonById(@PathVariable (name = "lessonId") Long lessonId) {
        LessonDto lesson = iLessonService.getLessonById(lessonId);

        // convert entity to DTO
        LessonDto lessonResponse = modelMapper.map(lesson, LessonDto.class);

        return ResponseEntity.ok().body(lessonResponse);
    }

    @PostMapping("/lesson")
    public ResponseEntity<LessonDto> createLesson(@RequestBody LessonDto lessonDto) {
        // convert DTO to entity
        Lesson lessonRequest = modelMapper.map(lessonDto, Lesson.class);

        Lesson lesson = iLessonService.createLesson(lessonRequest);

        // convert entity to DTO
        LessonDto lessonResponse = modelMapper.map(lesson, LessonDto.class);

        return new ResponseEntity<LessonDto>(lessonResponse, HttpStatus.CREATED);


    }

}
