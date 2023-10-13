package com.sep490.g49.shibadekiru.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonDto {

    private Long lessonId;
    private String name;
    private String content;
    private LocalDateTime createdAt;
    private Boolean status;
    private String image;
    private List<GrammarDto> grammarDtoList;
    private List<VocabularyDto> vocabularyDtoList;
    private List<KanjiDto> kanjiDtoList;
    private List<WritingDto> writingDtoList;
    private List<ListeningDto> listeningDtoList;
    private List<ReadingDto> readingDtoList;
    private BookDto bookDto;
}
