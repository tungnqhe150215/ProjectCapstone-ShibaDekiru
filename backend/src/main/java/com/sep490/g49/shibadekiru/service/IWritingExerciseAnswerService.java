package com.sep490.g49.shibadekiru.service;

import com.sep490.g49.shibadekiru.entity.Student;
import com.sep490.g49.shibadekiru.entity.WritingExercise;
import com.sep490.g49.shibadekiru.entity.WritingExerciseAnswer;

import java.util.List;

public interface IWritingExerciseAnswerService {
    WritingExerciseAnswer createWritingExerciseAnswer(WritingExerciseAnswer writingExerciseAnswer);

    WritingExerciseAnswer updateWritingExerciseAnswer(WritingExerciseAnswer answerRequest);

    List<WritingExerciseAnswer> getWritingExerciseAnswerByExerciseAndStudent(Long exerciseId, Long studentId);

    List<WritingExerciseAnswer> getWritingExerciseAnswerByClassworkAndStudent(Long classworkId, Long studentId);

    boolean checkWritingExerciseAnswerExist(Student student, WritingExercise writingExercise);
}
