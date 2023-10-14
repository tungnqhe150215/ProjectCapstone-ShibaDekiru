package com.sep490.g49.shibadekiru.dto;

import com.sep490.g49.shibadekiru.entity.Lesson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WritingDto {
    private Long writingId;
    private String topic;
    private Lesson lesson;
}
