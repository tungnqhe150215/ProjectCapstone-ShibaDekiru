package com.sep490.g49.shibadekiru.controller.lecture;

import com.sep490.g49.shibadekiru.dto.ClassWorkDto;
import com.sep490.g49.shibadekiru.dto.ExerciseDto;
import com.sep490.g49.shibadekiru.entity.ClassWork;
import com.sep490.g49.shibadekiru.entity.Exercise;
import com.sep490.g49.shibadekiru.service.IClassWorkService;
import com.sep490.g49.shibadekiru.service.IExerciseService;
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
@RequestMapping("/api/lecturer/class/classwork")
public class LectureManageExerciseController {
    @Autowired
    private IExerciseService iExerciseService;

    @Autowired
    private IClassWorkService iClassWorkService;

    @Autowired
    private ModelMapper map;

    @GetMapping("/{id}/exercise")
    public List<ExerciseDto> getExerciseByClassWork(@PathVariable(name = "id") Long classWorkId) {
        ClassWork classWorkResponse = iClassWorkService.getClassWorkById(classWorkId);
        return iExerciseService.getExercisePartByClasswork(classWorkResponse).stream().map(exercise ->map.map(exercise, ExerciseDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/exercise/{id}")
    public ResponseEntity<ExerciseDto> getExerciseById(@PathVariable(name = "id") Long id) {
        Exercise exercise = iExerciseService.getExerciseById(id);

        // convert entity to DTO
        ExerciseDto exerciseResponse = map.map(exercise, ExerciseDto.class);

        return ResponseEntity.ok().body(exerciseResponse);
    }

    @PostMapping( "/{id}/exercise")
    public ResponseEntity<ExerciseDto> createExercise(@RequestBody ExerciseDto exerciseDto, @PathVariable(name = "id") Long classWorkId) {
        ClassWorkDto classWorkDto = new ClassWorkDto();

        classWorkDto.setClassWorkId(classWorkId);

        exerciseDto.setClassWork(classWorkDto);
        // convert DTO to entity
        Exercise exerciseRequest = map.map(exerciseDto, Exercise.class);

        Exercise exercise = iExerciseService.createExercise(exerciseRequest);

        // convert entity to DTO
        ExerciseDto exerciseResponse = map.map(exercise, ExerciseDto.class);

        return new ResponseEntity<ExerciseDto>(exerciseResponse, HttpStatus.CREATED);
    }

    // change the request for DTO
    // change the response for DTO
    @PutMapping("/exercise/{id}")
    public ResponseEntity<ExerciseDto> updateExercise(@PathVariable long id, @RequestBody ExerciseDto exerciseDto) {

        // convert DTO to Entity
        Exercise exerciseRequest = map.map(exerciseDto, Exercise.class);

        Exercise exercise = iExerciseService.updateExercise(id, exerciseRequest);

        // entity to DTO
        ExerciseDto exerciseResponse = map.map(exercise, ExerciseDto.class);

        return ResponseEntity.ok().body(exerciseResponse);
    }

    @DeleteMapping("/exercise/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteExercise(@PathVariable(name = "id") Long id) {
        iExerciseService.deleteExercise(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

}
