package com.sep490.g49.shibadekiru.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAccountRegisterDto {
    private String nickName;
    private String firstName;
    private String lastName;
    private String memberId;
    private String email;
    private String password;
    private Boolean isActive;
    private Boolean isBanned;
    private Long roleId;
}
