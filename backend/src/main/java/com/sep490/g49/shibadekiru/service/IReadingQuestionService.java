package com.sep490.g49.shibadekiru.service;

import com.sep490.g49.shibadekiru.entity.Reading;
import com.sep490.g49.shibadekiru.entity.ReadingQuestion;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IReadingQuestionService {

    List<ReadingQuestion> getReadingQuestionByReading(Reading reading);

    ReadingQuestion getReadingQuestionById(Long id);

    ReadingQuestion createReadingQuestion(ReadingQuestion readingQuestion);

    ReadingQuestion updateReadingQuestion(Long id, ReadingQuestion readingQuestionRequest);

    void deleteReadingQuestion(Long id);
}
