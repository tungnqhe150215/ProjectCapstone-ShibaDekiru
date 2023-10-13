package com.sep490.g49.shibadekiru.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseDto {
    private Long exerciseId;
    private String title;
    private List<WritingExerciseDto> writingExerciseDtoList;
    private List<MultipleChoiceAnswerDto> multipleChoiceAnswerDtoList;
    private ClassWorkDto classWorkDto;
}
