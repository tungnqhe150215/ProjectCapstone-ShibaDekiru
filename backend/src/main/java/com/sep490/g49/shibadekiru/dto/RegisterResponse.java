package com.sep490.g49.shibadekiru.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterResponse {
    private String nickName;
    private String firstName;
    private String lastName;
    private String memberId;
    private String userName;
    private String email;
    private String password;
    private Long roleId;

}
