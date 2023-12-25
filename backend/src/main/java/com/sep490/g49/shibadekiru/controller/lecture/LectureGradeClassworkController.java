package com.sep490.g49.shibadekiru.controller.lecture;

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
@RequestMapping("/api/lecture")
public class LectureGradeClassworkController {

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
        if (iWritingExerciseAnswerService.getWritingExerciseAnswerByClassworkAndStudent(classworkId, studentId).size() > 0) {
            return iWritingExerciseAnswerService.getWritingExerciseAnswerByClassworkAndStudent(classworkId, studentId).stream().map(writingExerciseAnswer -> mapper.map(writingExerciseAnswer, WritingExerciseAnswerDto.class)).collect(Collectors.toList());
        } else
            return writingExerciseDtos;
    }

    @GetMapping("/classwork/{id}/submission")
    public List<StudentClassWorkDto> getStudentClassworkByClasswork(@PathVariable("id") Long classworkId ) {
        List<StudentClassWorkDto> studentClassWorkDtos = new ArrayList<>();
        ClassWork classWork = iClassWorkService.getClassWorkById(classworkId);
        if (iStudentClassWorkService.getStudentClassWorkByClassWork(classWork).size() > 0) {
            return iStudentClassWorkService.getStudentClassWorkByClassWork(classWork).stream().map(studentClassWork -> mapper.map(studentClassWork, StudentClassWorkDto.class)).collect(Collectors.toList());
        } else
            return studentClassWorkDtos;
    }

    @GetMapping("class/classwork/result")
    public List<StudentClassWorkDto> getStudentClassworkByClassAndStudent(@RequestParam("studentId") Long studentId, @RequestParam("classId") Long classId) {
        List<StudentClassWorkDto> studentClassWorkDto = new ArrayList<>();
        if (!iStudentClassWorkService.getStudentClassWorkByClassAndStudent(classId, studentId).isEmpty())
            return iStudentClassWorkService.getStudentClassWorkByClassAndStudent(classId, studentId).stream().map(studentClassWork -> mapper.map(studentClassWork, StudentClassWorkDto.class)).collect(Collectors.toList());
        else
            return studentClassWorkDto;
    }

    @GetMapping("exercise/answer")
    public List<WritingExerciseAnswerDto> getStudentWritingAnswerByExerciseAndStudent(@RequestParam("exerciseId") Long exerciseId, @RequestParam("studentId") Long studentId) {
        List<WritingExerciseAnswerDto> writingExerciseAnswerDtos = new ArrayList<>();
        if (!iWritingExerciseAnswerService.getWritingExerciseAnswerByExerciseAndStudent(exerciseId, studentId).isEmpty())
            return iWritingExerciseAnswerService.getWritingExerciseAnswerByExerciseAndStudent(exerciseId, studentId).stream().map(writingExerciseAnswer -> mapper.map(writingExerciseAnswer, WritingExerciseAnswerDto.class)).collect(Collectors.toList());
        else
            return writingExerciseAnswerDtos;
    }

    @PutMapping("/exercise/answer")
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

    @PutMapping("/exercise/result")
    public ResponseEntity<StudentClassWorkDto> createStudentClasswork(@RequestBody StudentClassWorkDto studentClassWorkDto) {

        StudentClassWork studentClassWorkRequest = mapper.map(studentClassWorkDto, StudentClassWork.class);

        StudentClassWork studentClassWork = new StudentClassWork();

        if (iStudentClassWorkService.checkStudentClassWorkExist(studentClassWorkRequest.getStudent(), studentClassWorkRequest.getClassWork())) {
            studentClassWorkRequest.setSubmitTime(iStudentClassWorkService.getStudentClassWorkByClassWorkAndStudent(studentClassWorkRequest.getClassWork().getClassWorkId(),studentClassWorkRequest.getStudent().getStudentId()).getSubmitTime());
            studentClassWork = iStudentClassWorkService.updateStudentClassWork(studentClassWorkRequest);
        } else {
            studentClassWork = iStudentClassWorkService.createStudentClassWork(studentClassWorkRequest);
        }
        StudentClassWorkDto studentClassWorkResponse = mapper.map(studentClassWork, StudentClassWorkDto.class);

        return new ResponseEntity<StudentClassWorkDto>(studentClassWorkResponse, HttpStatus.CREATED);
    }
}
