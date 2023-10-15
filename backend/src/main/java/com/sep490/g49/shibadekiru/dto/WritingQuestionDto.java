package com.sep490.g49.shibadekiru.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WritingQuestionDto {
    private Long writingQuestionId;

    private String question;

    private String sampleAnswer;

    private WritingDto writingDto;
}
