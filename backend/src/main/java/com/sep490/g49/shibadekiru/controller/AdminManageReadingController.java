package com.sep490.g49.shibadekiru.controller;

import com.sep490.g49.shibadekiru.dto.ReadingDto;
import com.sep490.g49.shibadekiru.dto.ReadingQuestionDto;
import com.sep490.g49.shibadekiru.entity.Lesson;
import com.sep490.g49.shibadekiru.entity.Reading;
import com.sep490.g49.shibadekiru.entity.ReadingQuestion;
import com.sep490.g49.shibadekiru.service.ILessonService;
import com.sep490.g49.shibadekiru.service.IReadingQuestionService;
import com.sep490.g49.shibadekiru.service.IReadingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin/lesson")
public class AdminManageReadingController {
    @Autowired
    private IReadingService iReadingService;
    
    @Autowired
    private IReadingQuestionService iReadingQuestionService;
    
    @Autowired
    private ILessonService iLessonService;

    @Autowired
    private ModelMapper map;

    @GetMapping("/{id}/reading")
    public List<ReadingDto> getReadingByLesson(@PathVariable(name = "id") Long lessonId) {
        Lesson lessonResponse = iLessonService.getLessonById(lessonId);
        return iReadingService.getReadingPartByLesson(lessonResponse).stream().map(reading ->map.map(reading, ReadingDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/reading/{id}")
    public ResponseEntity<ReadingDto> getReadingById(@PathVariable(name = "id") Long id) {
        Reading reading = iReadingService.getReadingById(id);

        // convert entity to DTO
        ReadingDto readingResponse = map.map(reading, ReadingDto.class);

        return ResponseEntity.ok().body(readingResponse);
    }

    @PostMapping( "/{id}/reading")
    public ResponseEntity<ReadingDto> createReading(@RequestBody ReadingDto readingDto, @PathVariable(name = "id") Long lessonId) {
        readingDto.setLessonId(iLessonService.getLessonById(lessonId).getLessonId());
        // convert DTO to entity
        Reading readingRequest = map.map(readingDto, Reading.class);

        Reading reading = iReadingService.createReading(readingRequest);

        // convert entity to DTO
        ReadingDto readingResponse = map.map(reading, ReadingDto.class);

        return new ResponseEntity<ReadingDto>(readingResponse, HttpStatus.CREATED);
    }

    // change the request for DTO
    // change the response for DTO
    @PutMapping("/reading/{id}")
    public ResponseEntity<ReadingDto> updateReading(@PathVariable long id, @RequestBody ReadingDto readingDto) {

        // convert DTO to Entity
        Reading readingRequest = map.map(readingDto, Reading.class);

        Reading reading = iReadingService.updateReading(id, readingRequest);

        // entity to DTO
        ReadingDto readingResponse = map.map(reading, ReadingDto.class);

        return ResponseEntity.ok().body(readingResponse);
    }

    @DeleteMapping("/reading/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteReading(@PathVariable(name = "id") Long id) {
        iReadingService.deleteReading(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/reading/{id}/reading-question")
    public List<ReadingQuestionDto> getReadingQuestionByReading(@PathVariable(name = "id") Long ReadingId) {
        Reading Reading = iReadingService.getReadingById(ReadingId);
        return iReadingQuestionService.getReadingQuestionByReading(Reading).stream().map(ReadingQuestion ->map.map(ReadingQuestion, ReadingQuestionDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/reading/reading-question/{id}")
    public ResponseEntity<ReadingQuestionDto> getReadingQuestionById(@PathVariable(name = "id") Long id) {
        ReadingQuestion ReadingQuestion = iReadingQuestionService.getReadingQuestionById(id);

        // convert entity to DTO
        ReadingQuestionDto ReadingResponse = map.map(ReadingQuestion, ReadingQuestionDto.class);

        return ResponseEntity.ok().body(ReadingResponse);
    }

    @PostMapping("/reading/{id}/reading-question")
    public ResponseEntity<ReadingQuestionDto> createReadingQuestion(@RequestBody ReadingQuestionDto ReadingQuestionDto, @PathVariable(name = "id") Long ReadingId) {
        Reading Reading = iReadingService.getReadingById(ReadingId);
        ReadingQuestionDto.setReadingDto(map.map(Reading,ReadingDto.class));
        // convert DTO to entity
        ReadingQuestion ReadingQuestionRequest = map.map(ReadingQuestionDto, ReadingQuestion.class);

        ReadingQuestion ReadingQuestion = iReadingQuestionService.createReadingQuestion(ReadingQuestionRequest);

        // convert entity to DTO
        ReadingQuestionDto ReadingQuestionResponse = map.map(ReadingQuestion, ReadingQuestionDto.class);

        return new ResponseEntity<ReadingQuestionDto>(ReadingQuestionResponse, HttpStatus.CREATED);
    }

    // change the request for DTO
    // change the response for DTO
    @PutMapping("/reading/reading-question/{id}")
    public ResponseEntity<ReadingQuestionDto> updateReadingQuestion(@PathVariable long id, @RequestBody ReadingQuestionDto ReadingQuestionDto) {

        // convert DTO to Entity
        ReadingQuestion ReadingQuestionRequest = map.map(ReadingQuestionDto, ReadingQuestion.class);

        ReadingQuestion ReadingQuestion = iReadingQuestionService.updateReadingQuestion(id, ReadingQuestionRequest);

        // entity to DTO
        ReadingQuestionDto ReadingQuestionResponse = map.map(ReadingQuestion, ReadingQuestionDto.class);

        return ResponseEntity.ok().body(ReadingQuestionResponse);
    }

    @DeleteMapping("/reading/reading-question/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteReadingQuestion(@PathVariable(name = "id") Long id) {
        iReadingQuestionService.deleteReadingQuestion(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
