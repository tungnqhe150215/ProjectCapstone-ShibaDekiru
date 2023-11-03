package com.sep490.g49.shibadekiru.controller.student;

import com.sep490.g49.shibadekiru.dto.*;
import com.sep490.g49.shibadekiru.entity.*;
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
@RequestMapping("/api")
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

    IListeningQuestionService listeningQuestionService;

    IWritingQuestionService writingQuestionService;

    IReadingQuestionService readingQuestionService;

    @GetMapping("/book")
    public List<BookDto> getAllBook(){
        return bookService.getAllBooks().stream().map(book -> modelMapper.map(book, BookDto.class)).collect(Collectors.toList());
    }
    @GetMapping("/book/{id}/lesson")
    public List<LessonDto> getAllLessonByBook(@PathVariable (name = "id") Long bookId) {
        Book bookResponse = bookService.getBookById(bookId);
        return lessonService.getLessonPartByBook(bookResponse).stream().map(lesson -> modelMapper.map(lesson, LessonDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/lesson/{lessonId}/reading")
    public List<ReadingDto> getAllReadingByLesson(@PathVariable (name = "lessonId") Long lessonId) {
        Lesson lessonResponse = lessonService.getLessonById(lessonId);
        return readingService.getAllReadingByLesson(lessonResponse).stream().map(reading -> modelMapper.map(reading, ReadingDto.class)).collect(Collectors.toList());
    }



    @GetMapping("/lesson/{lessonId}/writing")
    public List<WritingDto> getAllWritingByLesson(@PathVariable (name = "lessonId") Long lessonId) {
        Lesson lessonResponse = lessonService.getLessonById(lessonId);
        return writingService.getWritingPartByLesson(lessonResponse).stream().map(writing -> modelMapper.map(writing, WritingDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/lesson/{lessonId}/listening")
    public List<ListeningDto> getAllListeningByLesson(@PathVariable (name = "lessonId") Long lessonId) {
        Lesson lessonResponse = lessonService.getLessonById(lessonId);
        return listeningService.getListeningPartByLesson(lessonResponse).stream().map(listening -> modelMapper.map(listening, ListeningDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/lesson/{lessonId}/kanji")
    public List<KanjiDto> getAllKanjiByLesson(@PathVariable (name = "lessonId") Long lessonId) {
        Lesson lessonResponse = lessonService.getLessonById(lessonId);
        return kanjiService.getKanjiPartByLesson(lessonResponse).stream().map(kanji -> modelMapper.map(kanji, KanjiDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/lesson/{lessonId}/vocabulary")
    public List<VocabularyDto> getAllVocabularyByLesson(@PathVariable (name = "lessonId") Long lessonId) {
        Lesson lessonResponse = lessonService.getLessonById(lessonId);
        return vocabularyService.getVocabularyPartByLesson(lessonResponse).stream().map(vocabulary -> modelMapper.map(vocabulary, VocabularyDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/lesson/{lessonId}/grammar")
    public List<GrammarDto> getAllGrammarByLesson(@PathVariable (name = "lessonId") Long lessonId) {
        Lesson lessonResponse = lessonService.getLessonById(lessonId);
        return grammarService.getGrammarPartByLesson(lessonResponse).stream().map(grammar -> modelMapper.map(grammar, GrammarDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/lesson/{lessonId}/kaiwa")
    public List<KaiwaDto> getAllKaiwaByLesson(@PathVariable (name = "lessonId") Long lessonId) {
        Lesson lessonResponse = lessonService.getLessonById(lessonId);
        return kaiwaService.getKaiwaPartByLesson(lessonResponse).stream().map(kaiwa -> modelMapper.map(kaiwa, KaiwaDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/lesson/listening/{listeningId}/listening-question")
    public List<ListeningQuestionDto> getAllListeningQuestionByListening (@PathVariable (name = "listeningId") Long listeningId) {
        Listening listeningResponse = listeningService.getListeningById(listeningId);
        return listeningQuestionService.getListeningQuestionByListening(listeningResponse).stream().map(listeningQuestion -> modelMapper.map(listeningQuestion, ListeningQuestionDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/lesson/writing/{writingId}/writing-question")
    public List<WritingQuestionDto> getAllWritingQuestionByWriting (@PathVariable (name = "writingId") Long writingId) {
        Writing writingResponse = writingService.getWritingById(writingId);
        return writingQuestionService.getWritingQuestionByWriting(writingResponse).stream().map(writingQuestion -> modelMapper.map(writingQuestion, WritingQuestionDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/lesson/reading/{readingId}/reading-question")
    public List<ReadingQuestionDto> getAllReadingQuestionByReading (@PathVariable (name = "readingId") Long readingId) {
        Reading readingResponse = readingService.getReadingById(readingId);
        return readingQuestionService.getReadingQuestionByReading(readingResponse).stream().map(readingQuestion -> modelMapper.map(readingQuestion, ReadingQuestionDto.class)).collect(Collectors.toList());
    }

}
