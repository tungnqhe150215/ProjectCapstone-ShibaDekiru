package com.sep490.g49.shibadekiru.controller.admin;

import com.sep490.g49.shibadekiru.dto.ListeningDto;
import com.sep490.g49.shibadekiru.dto.ListeningQuestionDto;
import com.sep490.g49.shibadekiru.entity.Lesson;
import com.sep490.g49.shibadekiru.entity.Listening;
import com.sep490.g49.shibadekiru.entity.ListeningQuestion;
import com.sep490.g49.shibadekiru.service.GoogleDriveService;
import com.sep490.g49.shibadekiru.service.ILessonService;
import com.sep490.g49.shibadekiru.service.IListeningQuestionService;
import com.sep490.g49.shibadekiru.service.IListeningService;
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
public class AdminManageListeningController {
    @Autowired
    private IListeningService iListeningService;

    @Autowired
    private IListeningQuestionService iListeningQuestionService;

    @Autowired
    private ILessonService iLessonService;

    @Autowired
    private ModelMapper map;

    @Autowired
    private GoogleDriveService googleDriveService;

    @GetMapping("/{id}/listening")
    public List<ListeningDto> getListeningByLesson(@PathVariable(name = "id") Long lessonId) {
        Lesson lessonResponse = iLessonService.getLessonById(lessonId);
        return iListeningService.getListeningPartByLesson(lessonResponse).stream().map(listening ->map.map(listening, ListeningDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/listening/{id}")
    public ResponseEntity<ListeningDto> getListeningById(@PathVariable(name = "id") Long id) {
        Listening listening = iListeningService.getListeningById(id);
        if (listening.getLink().length() > 0 && !listening.getLink().equals("")){
            listening.setLink(googleDriveService.getFileUrl(listening.getLink()));
        }
        // convert entity to DTO
        ListeningDto listeningResponse = map.map(listening, ListeningDto.class);

        return ResponseEntity.ok().body(listeningResponse);
    }

    @PostMapping( "/{id}/listening")
    public ResponseEntity<ListeningDto> createListening(@RequestBody ListeningDto listeningDto, @PathVariable(name = "id") Long lessonId) {
        listeningDto.setLessonId(iLessonService.getLessonById(lessonId).getLessonId());
        // convert DTO to entity
        Listening listeningRequest = map.map(listeningDto, Listening.class);

        Listening listening = iListeningService.createListening(listeningRequest);

        // convert entity to DTO
        ListeningDto listeningResponse = map.map(listening, ListeningDto.class);

        return new ResponseEntity<ListeningDto>(listeningResponse, HttpStatus.CREATED);
    }

    // change the request for DTO
    // change the response for DTO
    @PutMapping("/listening/{id}")
    public ResponseEntity<ListeningDto> updateListening(@PathVariable long id, @RequestBody ListeningDto listeningDto) {

        // convert DTO to Entity
        Listening listeningRequest = map.map(listeningDto, Listening.class);

        Listening listening = iListeningService.updateListening(id, listeningRequest);

        // entity to DTO
        ListeningDto listeningResponse = map.map(listening, ListeningDto.class);

        return ResponseEntity.ok().body(listeningResponse);
    }

    @DeleteMapping("/listening/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteListening(@PathVariable(name = "id") Long id) {
        iListeningService.deleteListening(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/listening/{id}/listening-question")
    public List<ListeningQuestionDto> getListeningQuestionByListening(@PathVariable(name = "id") Long ListeningId) {
        Listening Listening = iListeningService.getListeningById(ListeningId);
        return iListeningQuestionService.getListeningQuestionByListening(Listening).stream().map(ListeningQuestion ->map.map(ListeningQuestion, ListeningQuestionDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/listening/listening-question/{id}")
    public ResponseEntity<ListeningQuestionDto> getListeningQuestionById(@PathVariable(name = "id") Long id) {
        ListeningQuestion ListeningQuestion = iListeningQuestionService.getListeningQuestionById(id);

        // convert entity to DTO
        ListeningQuestionDto ListeningResponse = map.map(ListeningQuestion, ListeningQuestionDto.class);

        return ResponseEntity.ok().body(ListeningResponse);
    }

    @PostMapping("/listening/{id}/listening-question")
    public ResponseEntity<ListeningQuestionDto> createListeningQuestion(@RequestBody ListeningQuestionDto ListeningQuestionDto, @PathVariable(name = "id") Long ListeningId) {
        Listening Listening = iListeningService.getListeningById(ListeningId);
        ListeningQuestionDto.setListeningDto(map.map(Listening,ListeningDto.class));
        // convert DTO to entity
        ListeningQuestion ListeningQuestionRequest = map.map(ListeningQuestionDto, ListeningQuestion.class);

        ListeningQuestion ListeningQuestion = iListeningQuestionService.createListeningQuestion(ListeningQuestionRequest);

        // convert entity to DTO
        ListeningQuestionDto ListeningQuestionResponse = map.map(ListeningQuestion, ListeningQuestionDto.class);

        return new ResponseEntity<ListeningQuestionDto>(ListeningQuestionResponse, HttpStatus.CREATED);
    }

    // change the request for DTO
    // change the response for DTO
    @PutMapping("/listening/listening-question/{id}")
    public ResponseEntity<ListeningQuestionDto> updateListeningQuestion(@PathVariable long id, @RequestBody ListeningQuestionDto ListeningQuestionDto) {

        // convert DTO to Entity
        ListeningQuestion ListeningQuestionRequest = map.map(ListeningQuestionDto, ListeningQuestion.class);

        ListeningQuestion ListeningQuestion = iListeningQuestionService.updateListeningQuestion(id, ListeningQuestionRequest);

        // entity to DTO
        ListeningQuestionDto ListeningQuestionResponse = map.map(ListeningQuestion, ListeningQuestionDto.class);

        return ResponseEntity.ok().body(ListeningQuestionResponse);
    }

    @DeleteMapping("/listening/listening-question/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteListeningQuestion(@PathVariable(name = "id") Long id) {
        iListeningQuestionService.deleteListeningQuestion(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
