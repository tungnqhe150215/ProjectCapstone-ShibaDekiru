package com.sep490.g49.shibadekiru.service;

import com.sep490.g49.shibadekiru.entity.ClassWork;
import com.sep490.g49.shibadekiru.entity.Exercise;
import com.sep490.g49.shibadekiru.entity.WritingExercise;

import java.util.List;

public interface IWritingExerciseService {
    List<WritingExercise> getWritingExerciseByExercise(Exercise exercise);

    WritingExercise getWritingExerciseById(Long id);

    WritingExercise createWritingExercise(WritingExercise writingExerciseRequest);

    WritingExercise updateWritingExercise(Long id, WritingExercise writingExerciseRequest);

    void deleteWritingExercise(Long id);

    List<WritingExercise> getWritingExerciseByClasswork(ClassWork classWork);
}
