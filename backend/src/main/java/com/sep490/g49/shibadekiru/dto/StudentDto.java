package com.sep490.g49.shibadekiru.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {
    private Long studentId;
    private String firstName;
    private String lastName;
    private String email;
    private Boolean gender;
    private String avatar;
    private UserAccountDto userAccount;
}
