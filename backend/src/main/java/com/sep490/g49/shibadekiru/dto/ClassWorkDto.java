package com.sep490.g49.shibadekiru.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassWorkDto {

    private Long classWorkId;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime deadline;
    private Boolean isLocked;
    private Long myCId;

}
