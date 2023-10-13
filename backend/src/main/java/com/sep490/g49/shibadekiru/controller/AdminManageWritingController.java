package com.sep490.g49.shibadekiru.controller;

import com.sep490.g49.shibadekiru.dto.LessonDto;
import com.sep490.g49.shibadekiru.dto.WritingDto;
import com.sep490.g49.shibadekiru.dto.WritingQuestionDto;
import com.sep490.g49.shibadekiru.entity.Lesson;
import com.sep490.g49.shibadekiru.entity.Writing;
import com.sep490.g49.shibadekiru.entity.WritingQuestion;
import com.sep490.g49.shibadekiru.service.ILessonService;
import com.sep490.g49.shibadekiru.service.IWritingQuestionService;
import com.sep490.g49.shibadekiru.service.IWritingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/admin/lesson")
public class AdminManageWritingController {
    @Autowired
    private IWritingService iWritingService;

    @Autowired
    private IWritingQuestionService iWritingQuestionService;

    @Autowired
    private ILessonService iLessonService;

    @Autowired
    private ModelMapper map;

    @GetMapping("/{id}/writing")
    public List<WritingDto> getWritingByLesson(@PathVariable(name = "id") Long lessonId) {
        LessonDto lessonResponse = iLessonService.getLessonById(lessonId);
        Lesson lesson = map.map(lessonResponse,Lesson.class);
        return iWritingService.getWritingPartByLesson(lesson).stream().map(writing ->map.map(writing, WritingDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/writing/{id}")
    public ResponseEntity<WritingDto> getWritingById(@PathVariable(name = "id") Long id) {
        Writing writing = iWritingService.getWritingById(id);

        // convert entity to DTO
        WritingDto writingResponse = map.map(writing, WritingDto.class);

        return ResponseEntity.ok().body(writingResponse);
    }

    @PostMapping("/{id}/writing")
    public ResponseEntity<WritingDto> createWriting(@RequestBody WritingDto writingDto) {

        // convert DTO to entity
        Writing writingRequest = map.map(writingDto, Writing.class);

        Writing writing = iWritingService.createWriting(writingRequest);

        // convert entity to DTO
        WritingDto writingResponse = map.map(writing, WritingDto.class);

        return new ResponseEntity<WritingDto>(writingResponse, HttpStatus.CREATED);
    }

    // change the request for DTO
    // change the response for DTO
    @PutMapping("/writing/{id}")
    public ResponseEntity<WritingDto> updateWriting(@PathVariable long id, @RequestBody WritingDto writingDto) {

        // convert DTO to Entity
        Writing writingRequest = map.map(writingDto, Writing.class);

        Writing writing = iWritingService.updateWriting(id, writingRequest);

        // entity to DTO
        WritingDto writingResponse = map.map(writing, WritingDto.class);

        return ResponseEntity.ok().body(writingResponse);
    }

    @DeleteMapping("/writing/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteWriting(@PathVariable(name = "id") Long id) {
        iWritingService.deleteWriting(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/writing/{id}/writing-question")
    public List<WritingQuestionDto> getWritingQuestionByWriting(@PathVariable(name = "id") Long writingId) {
        Writing writing = iWritingService.getWritingById(writingId);
        return iWritingQuestionService.getWritingQuestionByWriting(writing).stream().map(writingQuestion ->map.map(writingQuestion, WritingQuestionDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/writing/writing-question/{id}")
    public ResponseEntity<WritingDto> getWritingQuestionById(@PathVariable(name = "id") Long id) {
        Writing writing = iWritingService.getWritingById(id);

        // convert entity to DTO
        WritingDto writingResponse = map.map(writing, WritingDto.class);

        return ResponseEntity.ok().body(writingResponse);
    }

    @PostMapping("/writing/{id}/writing-question")
    public ResponseEntity<WritingQuestionDto> createWritingQuestion(@RequestBody WritingQuestionDto writingQuestionDto) {

        // convert DTO to entity
        WritingQuestion writingQuestionRequest = map.map(writingQuestionDto, WritingQuestion.class);

        WritingQuestion writingQuestion = iWritingQuestionService.createWritingQuestion(writingQuestionRequest);

        // convert entity to DTO
        WritingQuestionDto writingQuestionResponse = map.map(writingQuestion, WritingQuestionDto.class);

        return new ResponseEntity<WritingQuestionDto>(writingQuestionResponse, HttpStatus.CREATED);
    }

    // change the request for DTO
    // change the response for DTO
    @PutMapping("/writing/writing-question/{id}")
    public ResponseEntity<WritingQuestionDto> updateWritingQuestion(@PathVariable long id, @RequestBody WritingQuestionDto writingQuestionDto) {

        // convert DTO to Entity
        WritingQuestion writingQuestionRequest = map.map(writingQuestionDto, WritingQuestion.class);

        WritingQuestion writingQuestion = iWritingQuestionService.updateWritingQuestion(id, writingQuestionRequest);

        // entity to DTO
        WritingQuestionDto writingQuestionResponse = map.map(writingQuestion, WritingQuestionDto.class);

        return ResponseEntity.ok().body(writingQuestionResponse);
    }

    @DeleteMapping("/writing/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteWritingQuestion(@PathVariable(name = "id") Long id) {
        iWritingQuestionService.deleteWritingQuestion(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
