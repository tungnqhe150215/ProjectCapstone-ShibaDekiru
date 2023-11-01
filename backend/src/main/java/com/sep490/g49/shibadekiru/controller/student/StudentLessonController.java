package com.sep490.g49.shibadekiru.controller.student;

import com.sep490.g49.shibadekiru.dto.*;
import com.sep490.g49.shibadekiru.entity.Book;
import com.sep490.g49.shibadekiru.entity.Lesson;
import com.sep490.g49.shibadekiru.service.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/book")
public class StudentLessonController {

    ModelMapper modelMapper;

    IBookService bookService;

    ILessonService lessonService;

    IReadingService readingService;

    IWritingService writingService;

    IListeningService listeningService;

    IKanjiService kanjiService;

    IVocabularyService vocabularyService;

    IGrammarService grammarService;

    IKaiwaService kaiwaService;

    @GetMapping("/{id}/lesson")
    public List<LessonDto> getAllLessonByBook(@PathVariable (name = "id") Long bookId) {
        Book bookResponse = bookService.getBookById(bookId);
        return lessonService.getLessonPartByBook(bookResponse).stream().map(lessonDto -> modelMapper.map(lessonDto, LessonDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/lesson/{lessonId}/reading")
    public List<ReadingDto> getAllReadingByLesson(@PathVariable (name = "lessonId") Long lessonId) {
        Lesson lessonResponse = lessonService.getLessonById(lessonId);
        return readingService.getAllReadingByLesson(lessonResponse).stream().map(readingDto -> modelMapper.map(readingDto, ReadingDto.class)).collect(Collectors.toList());
    }



    @GetMapping("/lesson/{lessonId}/writing")
    public List<WritingDto> getAllWritingByLesson(@PathVariable (name = "lessonId") Long lessonId) {
        Lesson lessonResponse = lessonService.getLessonById(lessonId);
        return writingService.getWritingPartByLesson(lessonResponse).stream().map(writingDto -> modelMapper.map(writingDto, WritingDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/lesson/{lessonId}/listening")
    public List<ListeningDto> getAllListeningByLesson(@PathVariable (name = "lessonId") Long lessonId) {
        Lesson lessonResponse = lessonService.getLessonById(lessonId);
        return listeningService.getListeningPartByLesson(lessonResponse).stream().map(listeningDto -> modelMapper.map(listeningDto, ListeningDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/lesson/{lessonId}/kanji")
    public List<KanjiDto> getAllKanjiByLesson(@PathVariable (name = "lessonId") Long lessonId) {
        Lesson lessonResponse = lessonService.getLessonById(lessonId);
        return kanjiService.getKanjiPartByLesson(lessonResponse).stream().map(kanjiDto -> modelMapper.map(kanjiDto, KanjiDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/lesson/{lessonId}/vocabulary")
    public List<VocabularyDto> getAllVocabularyByLesson(@PathVariable (name = "lessonId") Long lessonId) {
        Lesson lessonResponse = lessonService.getLessonById(lessonId);
        return vocabularyService.getVocabularyPartByLesson(lessonResponse).stream().map(vocabularyDto -> modelMapper.map(vocabularyDto, VocabularyDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/lesson/{lessonId}/grammar")
    public List<GrammarDto> getAllGrammarByLesson(@PathVariable (name = "lessonId") Long lessonId) {
        Lesson lessonResponse = lessonService.getLessonById(lessonId);
        return grammarService.getGrammarPartByLesson(lessonResponse).stream().map(grammarDto -> modelMapper.map(grammarDto, GrammarDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/lesson/{lessonId}/kaiwa")
    public List<KaiwaDto> getAllKaiwaByLesson(@PathVariable (name = "lessonId") Long lessonId) {
        Lesson lessonResponse = lessonService.getLessonById(lessonId);
        return kaiwaService.getKaiwaPartByLesson(lessonResponse).stream().map(kaiwaDto -> modelMapper.map(kaiwaDto, KaiwaDto.class)).collect(Collectors.toList());
    }

//    @GetMapping("/lesson/listening/{listeningId}/listening-question")

}
