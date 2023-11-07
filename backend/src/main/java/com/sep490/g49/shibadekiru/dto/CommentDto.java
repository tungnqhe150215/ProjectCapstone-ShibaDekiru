package com.sep490.g49.shibadekiru.dto;


import com.sep490.g49.shibadekiru.entity.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long commentId;
    private String content;
    private LocalDateTime createdAt;
    private Long userAccountId;
    private Long postId;
}
