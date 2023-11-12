package com.sep490.g49.shibadekiru.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewPasswordDto {
    private String resetCode;
    private String newPassword;
    private String confirmNewPassword;
}
