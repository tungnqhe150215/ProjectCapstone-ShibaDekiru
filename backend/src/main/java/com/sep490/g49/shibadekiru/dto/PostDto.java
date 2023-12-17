package com.sep490.g49.shibadekiru.dto;

import com.sep490.g49.shibadekiru.entity.Lectures;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Long postId;
    private String postContent;
    private String description;
    private LocalDateTime createdAt;
    private Boolean isEnabled;
    private String image;
    private Long lectureId;
}
