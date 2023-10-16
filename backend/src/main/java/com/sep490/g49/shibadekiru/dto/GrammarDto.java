package com.sep490.g49.shibadekiru.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrammarDto {
    private Long grammarId;
    private String grammarName;
    private String grammarStructure;
    private String description;
    private String example;
    private Long lessonId;
}
