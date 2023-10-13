package com.sep490.g49.shibadekiru.dto;

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
    private UserAccountDto userAccountDto;
    private List<PostDto> postDtoList;
    private List<ClassDto> classDtoList;
    private List<TestDto> testDtoList;
}
