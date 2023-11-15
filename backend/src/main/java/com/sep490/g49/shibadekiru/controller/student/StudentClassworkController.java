package com.sep490.g49.shibadekiru.controller.student;

import com.sep490.g49.shibadekiru.dto.ClassWorkDto;
import com.sep490.g49.shibadekiru.dto.ExerciseDto;
import com.sep490.g49.shibadekiru.dto.WritingExerciseDto;
import com.sep490.g49.shibadekiru.entity.Class;
import com.sep490.g49.shibadekiru.entity.ClassWork;
import com.sep490.g49.shibadekiru.entity.Exercise;
import com.sep490.g49.shibadekiru.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private ModelMapper mapper;


    @GetMapping("classwork/{id}")
    public ClassWorkDto getClassWorkById(@PathVariable("id") Long id){
        ClassWorkDto classWorkDto = mapper.map(iClassWorkService.getClassWorkById(id),ClassWorkDto.class);
        return classWorkDto;
    }

    @GetMapping("/class/{id}/classwork")
    public List<ClassWorkDto> getClassWorkByClass(@PathVariable("id") Long id){
        Class aClass = iClassService.getClassById(id);
        return iClassWorkService.getClassWorkByClass(aClass).stream().map(classWork -> mapper.map(classWork,ClassWorkDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/classwork/{id}/exercise")
    public List<ExerciseDto> getExByClassWork(@PathVariable("id") Long id){
        ClassWork aClasswork = iClassWorkService.getClassWorkById(id);
        return iExerciseService.getExercisePartByClasswork(aClasswork).stream().map(exercise -> mapper.map(exercise,ExerciseDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/exercise/{id}/question")
    public List<WritingExerciseDto> getWritingQuestionByExercise(@PathVariable("id") Long id){
        Exercise exercise = iExerciseService.getExerciseById(id);
        return iWritingExerciseService.getWritingExerciseByExercise(exercise).stream().map(writingExercise -> mapper.map(writingExercise,WritingExerciseDto.class)).collect(Collectors.toList());
    }

}
