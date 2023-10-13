package com.sep490.g49.shibadekiru.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MultipleChoiceAnswerDto {
    private Long multipleChoiceAnswerId;
    private String firstChoice;
    private String secondChoice;
    private String thirdChoice;
    private String fourthChoice;
    private String correctAnswer;
    private List<ChoiceExerciseAnswerDto> choiceExerciseAnswerDtoList;
    private ExerciseDto exerciseDto;
}
