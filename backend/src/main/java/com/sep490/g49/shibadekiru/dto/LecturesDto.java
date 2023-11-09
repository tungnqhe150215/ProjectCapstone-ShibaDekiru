package com.sep490.g49.shibadekiru.dto;

import com.sep490.g49.shibadekiru.entity.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LecturesDto {
    private Long lectureId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String avatar;
    private String memberId;
}
