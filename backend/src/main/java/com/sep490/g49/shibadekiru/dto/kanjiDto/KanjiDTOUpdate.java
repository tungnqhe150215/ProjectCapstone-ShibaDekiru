package com.sep490.g49.shibadekiru.dto.kanjiDto;

import com.sep490.g49.shibadekiru.entity.Lesson;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class KanjiDTOUpdate {
    String characterKanji;
    String onReading;
    String kunReading;
    String chineseMean;
    String example;
    Lesson lesson;
}
