package com.sep490.g49.shibadekiru.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionBankDto {
    private Long questionBankId;
    private String question;
    private String firstChoice;
    private String secondChoice;
    private String thirdChoice;
    private String fourthChoice;
    private String correctAnswer;
    private TestDto testDto;
}
