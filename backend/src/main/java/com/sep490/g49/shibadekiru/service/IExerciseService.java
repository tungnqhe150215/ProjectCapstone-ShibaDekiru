package com.sep490.g49.shibadekiru.service;

import com.sep490.g49.shibadekiru.entity.ClassWork;
import com.sep490.g49.shibadekiru.entity.Exercise;

import java.util.List;

public interface IExerciseService {
    List<Exercise> getExercisePartByClasswork(ClassWork classWork);

    Exercise getExerciseById(Long id);

    Exercise createExercise(Exercise exercise);

    Exercise updateExercise(Long id, Exercise exerciseRequest);

    void deleteExercise(Long id);
}
