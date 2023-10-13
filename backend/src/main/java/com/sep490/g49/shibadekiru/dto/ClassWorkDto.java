package com.sep490.g49.shibadekiru.dto;

import com.sep490.g49.shibadekiru.entity.Exercise;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassWorkDto {

    private Long classWorkId;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime deadline;
    private Boolean isLocked;
    private List<StudentClassWorkDto> studentClassWorkDtoList;
    private List<ExerciseDto> exerciseDtoList;
}
