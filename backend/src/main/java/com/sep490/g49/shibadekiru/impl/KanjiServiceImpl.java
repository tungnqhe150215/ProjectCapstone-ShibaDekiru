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


}
