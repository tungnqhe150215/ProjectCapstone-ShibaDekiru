package com.sep490.g49.shibadekiru.service;

import com.sep490.g49.shibadekiru.entity.QuestionBank;
import com.sep490.g49.shibadekiru.entity.Test;
import com.sep490.g49.shibadekiru.entity.TestSection;

import java.util.List;

public interface IQuestionBankService {

    List<QuestionBank> getAllQuestionByTestSection(TestSection test);

    QuestionBank getQuestionById(long questionId);

    List<QuestionBank> getQuestionByTest(Test test);

    QuestionBank createQuestion(QuestionBank questionBank);

    QuestionBank updateQuestion(Long questionId, QuestionBank questionBankUpdate);

    void deleteQuestion(Long questionId);
}
