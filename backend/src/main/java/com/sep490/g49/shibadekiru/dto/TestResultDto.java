package com.sep490.g49.shibadekiru.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestResultDto {
    private Long testResultId;
    private Double result;
    private LocalDateTime doneTime;
    private StudentDto student;
    private TestSectionDto testSection;

}
