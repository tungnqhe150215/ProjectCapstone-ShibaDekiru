package com.sep490.g49.shibadekiru.dto;

import com.sep490.g49.shibadekiru.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Boolean isActive;
    private Boolean isBanned;
    private Boolean isCreatedByAdmin;
    private Role role;
}
