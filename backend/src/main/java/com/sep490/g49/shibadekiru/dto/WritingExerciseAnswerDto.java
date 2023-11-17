package com.sep490.g49.shibadekiru.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WritingExerciseAnswerDto {
    private Long writingExerciseAnswerId;
    private String answer;
    private String comment;
    private double mark;
    private StudentDto student;
    private WritingExerciseDto writingExercise;
}
