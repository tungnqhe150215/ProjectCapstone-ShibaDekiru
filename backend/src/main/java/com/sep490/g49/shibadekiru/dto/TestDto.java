package com.sep490.g49.shibadekiru.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestDto {
    private Long testId;
    private String title;
    private LocalDateTime createdAt;
    private Long duration;
    private Boolean isLocked;
    private LecturesDto lecture;
}
