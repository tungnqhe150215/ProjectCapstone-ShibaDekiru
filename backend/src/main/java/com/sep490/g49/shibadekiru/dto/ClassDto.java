package com.sep490.g49.shibadekiru.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassDto {
    private Long classId;
    private String className;
    private String classCode;
    private Boolean isLocked;
    private Long lectureId;
}
