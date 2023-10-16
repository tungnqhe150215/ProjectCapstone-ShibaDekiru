package com.sep490.g49.shibadekiru.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VocabularyDto {
    private Long vocabularyId;
    private String vocabularyName;
    private String hiragana;
    private String meaning;
    private String example;
    private Long lessonId;
}
