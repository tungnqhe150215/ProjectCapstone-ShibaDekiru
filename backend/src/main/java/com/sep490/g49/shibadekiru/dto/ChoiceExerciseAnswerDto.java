package com.sep490.g49.shibadekiru.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChoiceExerciseAnswerDto {

    private Long choiceExerciseAnswerId;
    private String answer;
    private String mark;
    private StudentDto studentDto;
    private MultipleChoiceAnswerDto multipleChoiceAnswerDto;
}
