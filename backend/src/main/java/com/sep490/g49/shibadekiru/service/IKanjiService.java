package com.sep490.g49.shibadekiru.service;

import com.sep490.g49.shibadekiru.entity.Kanji;

import java.util.Collection;
import java.util.List;

public interface IKanjiService {

    Kanji createKanji(Kanji kanji);

    List<Kanji> getAllKanji();


}
