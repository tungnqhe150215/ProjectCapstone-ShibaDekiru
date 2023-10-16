package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.Reading;
import com.sep490.g49.shibadekiru.entity.ReadingQuestion;
import com.sep490.g49.shibadekiru.repository.ReadingQuestionRepository;
import com.sep490.g49.shibadekiru.service.IReadingQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReadingQuestionServiceImpl implements IReadingQuestionService {

    @Autowired
    private ReadingQuestionRepository readingQuestionRepository;

    @Override
    public List<ReadingQuestion> getReadingQuestionByReading(Reading reading) {
        return readingQuestionRepository.findAllByReading(reading);
    }

    @Override
    public ReadingQuestion getReadingQuestionById(Long id) {
        return readingQuestionRepository.findReadingQuestionByReadingQuestionId(id);
    }

    @Override
    public ReadingQuestion createReadingQuestion(ReadingQuestion readingQuestion) {
        return readingQuestionRepository.save(readingQuestion);
    }

    @Override
    public ReadingQuestion updateReadingQuestion(Long id, ReadingQuestion readingQuestionRequest) {
        ReadingQuestion readingQuestion = readingQuestionRepository.findReadingQuestionByReadingQuestionId(id);
        readingQuestion.setQuestion(readingQuestionRequest.getQuestion());
        readingQuestion.setSampleAnswer(readingQuestionRequest.getSampleAnswer());
        return readingQuestionRepository.save(readingQuestion);
    }

    @Override
    public void deleteReadingQuestion(Long id) {
        ReadingQuestion readingQuestion = readingQuestionRepository.findReadingQuestionByReadingQuestionId(id);
        readingQuestionRepository.delete(readingQuestion);
    }
}
