package com.sep490.g49.shibadekiru.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordDto {

    private String currentPassword;
    private String newPassword;
    private String confirmationPassword;

}
