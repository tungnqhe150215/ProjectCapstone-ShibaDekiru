package com.sep490.g49.shibadekiru.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sep490.g49.shibadekiru.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationDto {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;

    private Long userAccountId;

    private String nickName;

    private String email;
    private String memberId;

    private Role role;
}
