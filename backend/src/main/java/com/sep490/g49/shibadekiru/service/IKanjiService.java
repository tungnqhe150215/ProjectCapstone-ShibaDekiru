package com.sep490.g49.shibadekiru.service;

import com.sep490.g49.shibadekiru.entity.Kanji;
import com.sep490.g49.shibadekiru.entity.Lesson;

import java.util.Collection;
import java.util.List;

public interface IKanjiService {

    List<Kanji> getKanjiPartByLesson(Lesson lesson);

    Kanji createKanji(Kanji kanji);

    List<Kanji> getAllKanji();

    Kanji getKanjiById(long kanjiId);

    Kanji updateKanji(Long kanjiId, Kanji kanjiUpdate);

    void deleteKanji(Long kanjiId);
}
