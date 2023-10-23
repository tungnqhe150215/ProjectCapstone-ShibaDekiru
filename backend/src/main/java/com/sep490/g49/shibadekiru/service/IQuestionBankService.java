package com.sep490.g49.shibadekiru.service;

import com.sep490.g49.shibadekiru.entity.QuestionBank;

import java.util.Collection;
import java.util.List;

public interface IQuestionBankService {
    List<QuestionBank> getAllQuestion();

    QuestionBank getQuestionById(long questionId);

    QuestionBank createQuestion(QuestionBank questionBank);

    QuestionBank updateQuestion(Long questionId, QuestionBank questionBankUpdate);

    void deleteQuestion(Long questionId);
}
