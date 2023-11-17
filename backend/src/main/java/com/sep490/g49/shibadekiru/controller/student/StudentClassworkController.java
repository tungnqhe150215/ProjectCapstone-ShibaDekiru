package com.sep490.g49.shibadekiru.controller.student;

import com.sep490.g49.shibadekiru.dto.*;
import com.sep490.g49.shibadekiru.entity.*;
import com.sep490.g49.shibadekiru.entity.Class;
import com.sep490.g49.shibadekiru.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/student")
public class StudentClassworkController {

    @Autowired
    private IClassService iClassService;

    @Autowired
    private IClassWorkService iClassWorkService;

    @Autowired
    private IStudentClassWorkService iStudentClassWorkService;

    @Autowired
    private IStudentService iStudentService;

    @Autowired
    private IExerciseService iExerciseService;

    @Autowired
    private IWritingExerciseService iWritingExerciseService;

    @Autowired
    private IWritingExerciseAnswerService iWritingExerciseAnswerService;

    @Autowired
    private ModelMapper mapper;


    @GetMapping("classwork/{id}")
    public ClassWorkDto getClassWorkById(@PathVariable("id") Long id) {
        ClassWorkDto classWorkDto = mapper.map(iClassWorkService.getClassWorkById(id), ClassWorkDto.class);
        return classWorkDto;
    }

    @GetMapping("/class/{id}/classwork")
    public List<ClassWorkDto> getClassWorkByClass(@PathVariable("id") Long id) {
        Class aClass = iClassService.getClassById(id);
        return iClassWorkService.getClassWorkByClass(aClass).stream().map(classWork -> mapper.map(classWork, ClassWorkDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/classwork/{id}/exercise")
    public List<ExerciseDto> getExByClassWork(@PathVariable("id") Long id) {
        ClassWork aClasswork = iClassWorkService.getClassWorkById(id);
        return iExerciseService.getExercisePartByClasswork(aClasswork).stream().map(exercise -> mapper.map(exercise, ExerciseDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/exercise/{id}/question")
    public List<WritingExerciseDto> getWritingQuestionByExercise(@PathVariable("id") Long id) {
        Exercise exercise = iExerciseService.getExerciseById(id);
        return iWritingExerciseService.getWritingExerciseByExercise(exercise).stream().map(writingExercise -> mapper.map(writingExercise, WritingExerciseDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/classwork/{id}/question")
    public List<WritingExerciseDto> getWritingQuestionByClasswork(@PathVariable("id") Long id) {
        ClassWork classWork = iClassWorkService.getClassWorkById(id);
        return iWritingExerciseService.getWritingExerciseByClasswork(classWork).stream().map(writingExercise -> mapper.map(writingExercise, WritingExerciseDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/classwork/result")
    public StudentClassWorkDto getStudentClassworkByClassworkAndStudent(@RequestParam("studentId") Long studentId, @RequestParam("classworkId") Long classworkId) {
        StudentClassWorkDto studentClassWorkDto = mapper.map(iStudentClassWorkService.getStudentClassWorkByClassWorkAndStudent(classworkId, studentId), StudentClassWorkDto.class);
        return studentClassWorkDto;
    }

    @GetMapping("/classwork/answer")
    public List<WritingExerciseAnswerDto> getStudentAnswerByClassworkAndStudent(@RequestParam("studentId") Long studentId, @RequestParam("classworkId") Long classworkId) {
        List<WritingExerciseAnswerDto> writingExerciseDtos = new ArrayList<>();
        if (iWritingExerciseAnswerService.getWritingExerciseAnswerByExerciseAndStudent(classworkId, studentId).size() > 0) {
            return iWritingExerciseAnswerService.getWritingExerciseAnswerByExerciseAndStudent(classworkId, studentId).stream().map(writingExerciseAnswer -> mapper.map(writingExerciseAnswer, WritingExerciseAnswerDto.class)).collect(Collectors.toList());
        } else
            return writingExerciseDtos;
    }

    @PostMapping("/exercise/answer")
    public ResponseEntity<WritingExerciseAnswerDto> createWritingExerciseAnswer(@RequestBody WritingExerciseAnswerDto writingExerciseAnswerDto) {

        WritingExerciseAnswer writingExerciseAnswerRequest = mapper.map(writingExerciseAnswerDto, WritingExerciseAnswer.class);

        WritingExerciseAnswer writingExerciseAnswer = new WritingExerciseAnswer();

        if (iWritingExerciseAnswerService.checkWritingExerciseAnswerExist(writingExerciseAnswerRequest.getStudent(), writingExerciseAnswerRequest.getWritingExercise())) {
            writingExerciseAnswer = iWritingExerciseAnswerService.updateWritingExerciseAnswer(writingExerciseAnswerRequest);
        } else {
            writingExerciseAnswer = iWritingExerciseAnswerService.createWritingExerciseAnswer(writingExerciseAnswerRequest);
        }
        WritingExerciseAnswerDto writingExerciseAnswerResponse = mapper.map(writingExerciseAnswer, WritingExerciseAnswerDto.class);

        return new ResponseEntity<WritingExerciseAnswerDto>(writingExerciseAnswerResponse, HttpStatus.CREATED);
    }

    @PostMapping("/exercise/result")
    public ResponseEntity<StudentClassWorkDto> createStudentClasswork(@RequestBody StudentClassWorkDto studentClassWorkDto) {

        StudentClassWork studentClassWorkRequest = mapper.map(studentClassWorkDto, StudentClassWork.class);

        StudentClassWork studentClassWork = new StudentClassWork();

        if (iStudentClassWorkService.checkStudentClassWorkExist(studentClassWorkRequest.getStudent(), studentClassWorkRequest.getClassWork())) {
            studentClassWork = iStudentClassWorkService.updateStudentClassWork(studentClassWorkRequest);
        } else {
            studentClassWork = iStudentClassWorkService.createStudentClassWork(studentClassWorkRequest);
        }
        StudentClassWorkDto studentClassWorkResponse = mapper.map(studentClassWork, StudentClassWorkDto.class);

        return new ResponseEntity<StudentClassWorkDto>(studentClassWorkResponse, HttpStatus.CREATED);
    }
}
