package com.sep490.g49.shibadekiru.controller.admin;

import com.sep490.g49.shibadekiru.dto.LessonDto;
import com.sep490.g49.shibadekiru.entity.Book;
import com.sep490.g49.shibadekiru.entity.Lesson;
import com.sep490.g49.shibadekiru.repository.LessonRepository;
import com.sep490.g49.shibadekiru.service.GoogleDriveService;
import com.sep490.g49.shibadekiru.service.IBookService;
import com.sep490.g49.shibadekiru.service.ILessonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminManageLessonController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ILessonService iLessonService;

    @Autowired
    private IBookService iBookService;

    @Autowired
    private GoogleDriveService googleDriveService;


    @GetMapping("/lesson")
    public List<LessonDto> getAllLessons() {
      return iLessonService.getAllLessons().stream().map(lesson -> modelMapper.map(lesson, LessonDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/book/{id}/lesson")
    public List<LessonDto> getAllLessonByBook(@PathVariable (name = "id") Long bookId) {
        Book book = iBookService.getBookById(bookId);
        return iLessonService.getLessonPartByBook(book).stream().map(lesson -> modelMapper.map(lesson, LessonDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/book/lesson/{lessonId}")
    public ResponseEntity<LessonDto> getLessonById(@PathVariable (name = "lessonId") Long lessonId) {
        Lesson lesson = iLessonService.getLessonById(lessonId);
        if (lesson.getImage().length() > 0 && ! lesson.getImage().equals("")) {
            lesson.setImage(googleDriveService.getFileUrl(lesson.getImage()));
        }

        // convert entity to DTO
        LessonDto lessonResponse = modelMapper.map(lesson, LessonDto.class);

        return ResponseEntity.ok().body(lessonResponse);
    }


    @PostMapping("/book/{id}/lesson")
    public ResponseEntity<LessonDto> createLesson(@PathVariable (name = "id") Long bookId ,@RequestBody LessonDto lessonDto) {

        lessonDto.setBookId(iBookService.getBookById(bookId).getBookId());

        Lesson lessonRequest =  modelMapper.map(lessonDto, Lesson.class);

        Lesson lesson =  iLessonService.createLesson(lessonRequest);

        LessonDto lessonResponse = modelMapper.map(lesson, LessonDto.class);

        return new ResponseEntity<LessonDto>(lessonResponse, HttpStatus.CREATED);
    }

    @PutMapping("/lesson/{lessonId}")
    public ResponseEntity<LessonDto> updateLesson(
            @PathVariable Long lessonId,
            @RequestBody LessonDto lessonDto
    ) {
        
        Lesson lessonRequest =  modelMapper.map(lessonDto, Lesson.class);

        Lesson lesson =  iLessonService.updateLesson(lessonId ,lessonRequest);

        LessonDto lessonResponse = modelMapper.map(lesson, LessonDto.class);

        return ResponseEntity.ok().body(lessonResponse);
    }

    @DeleteMapping("/lesson/{lessonId}")
    public ResponseEntity<Map<String, Boolean>> deleteLesson(@PathVariable Long lessonId) {
        iLessonService.deleteLesson(lessonId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return ResponseEntity.ok(response);
    }


}
