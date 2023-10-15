package com.sep490.g49.shibadekiru.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class KanjiDto {
    private Long kanjiId;
    private String characterKanji;
    private String onReading;
    private String kunReading;
    private String chineseMean;
    private String example;
    private Long lessonId;
}
