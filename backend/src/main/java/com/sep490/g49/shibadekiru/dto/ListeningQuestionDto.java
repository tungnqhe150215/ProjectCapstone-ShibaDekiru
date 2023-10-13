package com.sep490.g49.shibadekiru.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListeningQuestionDto {
    private Long listeningQuestionId;
    private String question;
    private String firstChoice;
    private String secondChoice;
    private String thirdChoice;
    private String correctAnswer;
    private ListeningDto listeningDto;
}
