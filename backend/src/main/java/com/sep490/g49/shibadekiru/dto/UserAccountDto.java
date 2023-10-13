package com.sep490.g49.shibadekiru.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAccountDto {
    private Long userAccountId;
    private String nickName;
    private String memberId;
    private String userName;
    private String password;
    private String email;
    private String resetCode;
    private Boolean isBanned;
    private ChatDto chatDto;
    private StudentDto studentDto;
    private LecturesDto lecturesDto;
    private List<CommentDto> commentDtoList;
    private RoleDto roleDto;
}
