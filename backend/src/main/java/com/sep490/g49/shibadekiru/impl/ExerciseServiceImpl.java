package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.ClassWork;
import com.sep490.g49.shibadekiru.entity.Exercise;
import com.sep490.g49.shibadekiru.repository.ExerciseRepository;
import com.sep490.g49.shibadekiru.service.IExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseServiceImpl implements IExerciseService {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Override
    public List<Exercise> getExercisePartByClasswork(ClassWork classWork) {
        return exerciseRepository.findExercisesByClassWorkAndIsDeletedFalse(classWork);
    }

    @Override
    public Exercise getExerciseById(Long id) {
        return exerciseRepository.findByExerciseId(id);
    }

    @Override
    public Exercise createExercise(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }

    @Override
    public Exercise updateExercise(Long id, Exercise exerciseRequest) {
        Exercise exercise = exerciseRepository.findByExerciseId(id);
        exercise.setTitle(exerciseRequest.getTitle());
        return exerciseRepository.save(exercise);
    }

    @Override
    public void deleteExercise(Long id) {
        Exercise exercise = exerciseRepository.findByExerciseId(id);
        exercise.setIsDeleted(true);
        exerciseRepository.save(exercise);
    }
}
