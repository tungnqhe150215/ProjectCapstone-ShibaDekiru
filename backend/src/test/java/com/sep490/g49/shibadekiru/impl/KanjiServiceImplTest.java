package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.Grammar;
import com.sep490.g49.shibadekiru.entity.Kanji;
import com.sep490.g49.shibadekiru.entity.Lesson;
import com.sep490.g49.shibadekiru.entity.Vocabulary;
import com.sep490.g49.shibadekiru.repository.GrammarRepository;
import com.sep490.g49.shibadekiru.repository.KanjiRepository;
import com.sep490.g49.shibadekiru.repository.LessonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class KanjiServiceImplTest {
    @Mock
    KanjiRepository kanjiRepository;

    @Mock
    LessonRepository lessonRepository;

    @InjectMocks
    KanjiServiceImpl kanjiService;
    @BeforeEach
    void setUp() { MockitoAnnotations.openMocks(this); }

    @Test
    void getKanjiPartByLesson() {
        // Arrange
        Lesson lesson = new Lesson();
        List kanjiList = new ArrayList<>();
        kanjiList.add(new Kanji());
        kanjiList.add(new Kanji());
        when(kanjiRepository.findKanjiByLesson(lesson)).thenReturn(kanjiList);

        // Act
        List result = kanjiService.getKanjiPartByLesson(lesson);

        // Assert
        assertEquals(2, result.size()); verify(kanjiRepository, times(1)).findKanjiByLesson(lesson);
    }

//    @Test
//    void createKanji() {
//        // Arrange
//        Kanji kanjiRequest = new Kanji();
//        Lesson lesson = new Lesson();
//        kanjiRequest.setLesson(lesson);
//
//        when(lessonRepository.findById(anyLong())).thenReturn(Optional.of(lesson));
//        when(kanjiRepository.save(any())).thenReturn(kanjiRequest);
//
//        // Act
//        Kanji result = kanjiService.createKanji(kanjiRequest);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(kanjiRequest, result);
//        verify(lessonRepository, times(1)).findById(anyLong());
//        verify(kanjiRepository, times(1)).save(any());
//    }

    @Test
    void getAllKanji() {
        // Arrange
        List<Kanji> kanjiList = new ArrayList<>();
        kanjiList.add(new Kanji());
        when(kanjiRepository.findAll()).thenReturn(kanjiList);

        // Act
        List<Kanji> result = kanjiService.getAllKanji();

        // Assert
        Assertions.assertEquals(kanjiList, result);
    }

    @Test
    void getKanjiById() {
        // Arrange
        Long kanjiId = 1L;
        Kanji kanji = new Kanji();
        when(kanjiRepository.findById(kanjiId)).thenReturn(Optional.of(kanji));

        // Act
        Kanji result = kanjiService.getKanjiById(kanjiId);

        // Assert
        Assertions.assertEquals(kanji, result);
    }

    @Test
    void updateKanji() {
        // Arrange
        Long kanjiId = 1L;
        Kanji existingKanji = new Kanji();
        Kanji updatedKanji = new Kanji();
        updatedKanji.setCharacterKanji("Updated Kanji");
        updatedKanji.setLesson(new Lesson());

        when(kanjiRepository.findById(kanjiId)).thenReturn(Optional.of(existingKanji));
        when(kanjiRepository.save(any())).thenReturn(updatedKanji);

        // Act
        Kanji result = kanjiService.updateKanji(kanjiId, updatedKanji);

        // Assert
        assertNotNull(result);
        assertEquals(updatedKanji.getCharacterKanji(), result.getCharacterKanji());
        verify(kanjiRepository, times(1)).findById(kanjiId);
        verify(kanjiRepository, times(1)).save(any());
    }

    @Test
    void deleteKanji() {
        // Arrange
        Long kanjiId = 1L;
        Kanji kanji = new Kanji();
        when(kanjiRepository.findById(kanjiId)).thenReturn(Optional.of(kanji));

        // Act
        kanjiService.deleteKanji(kanjiId);

        // Assert
        verify(kanjiRepository, times(1)).findById(kanjiId);
        verify(kanjiRepository, times(1)).delete(kanji);
    }
}