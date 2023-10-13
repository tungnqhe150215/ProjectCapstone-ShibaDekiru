package com.sep490.g49.shibadekiru.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadingQuestionDto {
    private Long readingQuestionId;
    private String question;
    private String sampleAnswer;
    private ReadingDto readingDto;
}
