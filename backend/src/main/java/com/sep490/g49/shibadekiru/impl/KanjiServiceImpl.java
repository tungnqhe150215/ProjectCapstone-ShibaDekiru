package com.sep490.g49.shibadekiru.impl;


import com.sep490.g49.shibadekiru.entity.Kanji;
import com.sep490.g49.shibadekiru.entity.Lesson;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;

import com.sep490.g49.shibadekiru.repository.KanjiRepository;
import com.sep490.g49.shibadekiru.repository.LessonRepository;
import com.sep490.g49.shibadekiru.service.IKanjiService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KanjiServiceImpl implements IKanjiService {

    KanjiRepository kanjiRepository;

    LessonRepository lessonRepository;


    @Override
    public List<Kanji> getKanjiPartByLesson(Lesson lesson) {
        return kanjiRepository.findKanjiByLesson(lesson);
    }

    @Override
    public Kanji createKanji(Kanji kanji) {

        String characterKanji = kanji.getCharacterKanji();
        String onReading = kanji.getOnReading();
        String kunReading = kanji.getKunReading();
        String chineseMean = kanji.getChineseMean();
        String example = kanji.getExample();
        Long lessonId = kanji.getLesson().getLessonId();

        Optional<Lesson> lessonOptional = lessonRepository.findById(lessonId);

        if(lessonOptional.isPresent()) {
            Lesson lesson = lessonOptional.get();

            Kanji kanji1 = new Kanji();
            kanji1.setCharacterKanji(characterKanji);
            kanji1.setOnReading(onReading);
            kanji1.setKunReading(kunReading);
            kanji1.setChineseMean(chineseMean);
            kanji1.setExample(example);
            kanji1.setLesson(lesson);

            return kanjiRepository.save(kanji1);

        } else {
            throw new ResourceNotFoundException("Kanji can't be added.");
        }
    }

    @Override
    public List<Kanji> getAllKanji() {
        return kanjiRepository.findAll();
    }

    @Override
    public Kanji getKanjiById(long kanjiId) {

        Kanji kanji = kanjiRepository.findById(kanjiId).orElse(null);

        if(kanji == null) {
            throw new ResourceNotFoundException("Kanji not found with id: " +  kanjiId);
        }
        return kanji;
    }

    @Override
    public Kanji updateKanji(Long kanjiId, Kanji kanjiUpdate) {

        Optional<Kanji> kanjiOptional = kanjiRepository.findById(kanjiId);

        if(kanjiOptional.isPresent()) {
            Kanji kanji = kanjiOptional.get();

            kanji.setCharacterKanji(kanjiUpdate.getCharacterKanji());
            kanji.setOnReading(kanjiUpdate.getOnReading());
            kanji.setKunReading(kanjiUpdate.getKunReading());
            kanji.setChineseMean(kanjiUpdate.getChineseMean());
            kanji.setExample(kanjiUpdate.getExample());
            kanji.setLesson(kanjiUpdate.getLesson());

            return kanjiRepository.save(kanji);
        } else {
            throw new ResourceNotFoundException("Kanji not found");
        }
    }

    @Override
    public void deleteKanji(Long kanjiId) {
        Kanji kanji = kanjiRepository.findById(kanjiId).orElseThrow(() -> new ResourceNotFoundException("Kanji not exist with id:" + kanjiId));
        kanjiRepository.delete(kanji);
    }
}
