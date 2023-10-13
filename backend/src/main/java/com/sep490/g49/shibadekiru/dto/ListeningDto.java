package com.sep490.g49.shibadekiru.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListeningDto {
    private Long listeningId;
    private String title;
    private String link;
    private String script;
    private List<ListeningQuestionDto> listeningQuestionDtoList;
    private LessonDto lessonDto;
}
