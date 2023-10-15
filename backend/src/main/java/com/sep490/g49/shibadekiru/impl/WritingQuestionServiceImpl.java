package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.Writing;
import com.sep490.g49.shibadekiru.entity.WritingQuestion;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.repository.WritingQuestionRepository;
import com.sep490.g49.shibadekiru.service.IWritingQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WritingQuestionServiceImpl implements IWritingQuestionService {

    @Autowired
    private WritingQuestionRepository writingQuestionRepository;

    @Override
    public List<WritingQuestion> getWritingQuestionByWriting(Writing writing) {
        return writingQuestionRepository.findAllByWriting(writing);
    }

    @Override
    public WritingQuestion getWritingQuestionById(Long id) {
        WritingQuestion writingQuestion = writingQuestionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found data"));
        return writingQuestion;
    }

    @Override
    public WritingQuestion createWritingQuestion(WritingQuestion writingQuestionRequest) {
        return writingQuestionRepository.save(writingQuestionRequest);
    }

    @Override
    public WritingQuestion updateWritingQuestion(Long id, WritingQuestion writingQuestionRequest) {
        WritingQuestion writingQuestion = writingQuestionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found data"));
        writingQuestion.setQuestion(writingQuestionRequest.getQuestion());
        writingQuestion.setSampleAnswer(writingQuestionRequest.getSampleAnswer());
        return writingQuestionRepository.save(writingQuestion);
    }

    @Override
    public void deleteWritingQuestion(Long id) {
        WritingQuestion writingQuestion = writingQuestionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found data"));
        writingQuestionRepository.delete(writingQuestion);
    }
}
