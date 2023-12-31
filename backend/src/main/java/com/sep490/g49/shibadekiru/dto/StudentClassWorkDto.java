package com.sep490.g49.shibadekiru.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentClassWorkDto {
    private Long studentClassWorkId;
    private Double result;
    private LocalDateTime submitTime;
    private Boolean isGraded;
    private StudentDto student;
    private ClassWorkDto classWork;

}
