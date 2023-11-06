package com.sep490.g49.shibadekiru.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileAnswerDto {

    private Long fileAnswerId;
    private String fileAnswer;
    private String mark;
    private StudentDto student;
    private SubmitFileExerciseDto submitFileExercise;
}
