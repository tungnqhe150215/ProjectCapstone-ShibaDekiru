package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.dto.VocabularyDto;
import com.sep490.g49.shibadekiru.entity.Lesson;
import com.sep490.g49.shibadekiru.entity.Vocabulary;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.repository.LessonRepository;
import com.sep490.g49.shibadekiru.repository.VocabularyRepository;
import com.sep490.g49.shibadekiru.service.IVocabularyService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VocabularyServiceImpl implements IVocabularyService {

    VocabularyRepository vocabularyRepository;

    LessonRepository lessonRepository;

    @Override
    public List<Vocabulary> getAllVocabulary() {
        return vocabularyRepository.findAll();
    }

    @Override
    public Vocabulary getVocabularyById(long vocabularyId) {
        Vocabulary vocabulary = vocabularyRepository.findById(vocabularyId).orElse(null);

        if(vocabulary == null) {
            throw new ResourceNotFoundException("Vocabulary not found with id: " + vocabularyId);
        }

        return vocabulary;
    }

    @Override
    public Vocabulary createVocabulary(Vocabulary vocabulary) {

        String vocabularyName = vocabulary.getVocabularyName();
        String hiragana = vocabulary.getHiragana();
        String meaning = vocabulary.getMeaning();
        String example = vocabulary.getExample();
        Long lessonId = vocabulary.getLesson().getLessonId();

        Optional<Lesson> lessonOptional = lessonRepository.findById(lessonId);

        if(lessonOptional.isPresent()) {
            Lesson lesson = lessonOptional.get();

            Vocabulary vocabulary1 = new Vocabulary();
            vocabulary1.setVocabularyName(vocabularyName);
            vocabulary1.setHiragana(hiragana);
            vocabulary1.setMeaning(meaning);
            vocabulary1.setExample(example);
            vocabulary1.setLesson(lesson);

            return vocabularyRepository.save(vocabulary1);
        } else {
            throw new ResourceNotFoundException("Vocabulary can't be added.");
        }
    }

    @Override
    public Vocabulary updateVocabulary(Long vocabularyId, Vocabulary vocabularyUpdate) {

        Optional<Vocabulary> vocabularyOptional = vocabularyRepository.findById(vocabularyId);

        if(vocabularyOptional.isPresent()) {
            Vocabulary vocabulary = vocabularyOptional.get();

            vocabulary.setVocabularyName(vocabularyUpdate.getVocabularyName());
            vocabulary.setHiragana(vocabularyUpdate.getHiragana());
            vocabulary.setMeaning(vocabularyUpdate.getMeaning());
            vocabulary.setExample(vocabularyUpdate.getExample());
            vocabulary.setLesson(vocabularyUpdate.getLesson());

            return vocabularyRepository.save(vocabulary);
        } else {
            throw new ResourceNotFoundException("Vocabulary not found");
        }
    }

    @Override
    public void deleteVocabulary(Long vocabularyId) {
        Vocabulary vocabulary = vocabularyRepository.findById(vocabularyId).orElseThrow(() -> new ResourceNotFoundException("Kanji not exist with id:" + vocabularyId));
        vocabularyRepository.delete(vocabulary);
    }
}
