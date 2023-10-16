package com.sep490.g49.shibadekiru.service;

import com.sep490.g49.shibadekiru.entity.Vocabulary;

import java.util.Collection;
import java.util.List;

public interface IVocabularyService {
    List<Vocabulary> getAllVocabulary();

    Vocabulary getVocabularyById(long vocabularyId);

    Vocabulary createVocabulary(Vocabulary vocabulary);

    Vocabulary updateVocabulary(Long vocabularyId, Vocabulary vocabularyUpdate);

    void deleteVocabulary(Long vocabularyId);
}
