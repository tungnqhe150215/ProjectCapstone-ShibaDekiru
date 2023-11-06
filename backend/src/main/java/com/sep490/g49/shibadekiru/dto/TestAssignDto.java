package com.sep490.g49.shibadekiru.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestAssignDto {
    private Long id;
    private LocalDateTime accessExpirationDate;
    private ClassDto assignedClass;
    private TestDto test;
}
