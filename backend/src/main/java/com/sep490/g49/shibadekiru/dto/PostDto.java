package com.sep490.g49.shibadekiru.dto;

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
    private Boolean status;
    private List<CommentDto> commentDtoList;
    private LecturesDto lecturesDto;
}
