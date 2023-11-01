package com.sep490.g49.shibadekiru.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassStudentDto {

    private Long classStudentId;
    private LocalDateTime joinedAt;
    private StudentDto student;
    private ClassDto belongClass;
}
