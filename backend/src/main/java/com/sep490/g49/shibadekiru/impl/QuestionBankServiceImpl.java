package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.QuestionBank;
import com.sep490.g49.shibadekiru.entity.Test;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.repository.QuestionBankRepository;
import com.sep490.g49.shibadekiru.repository.TestRepository;
import com.sep490.g49.shibadekiru.service.IQuestionBankService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class QuestionBankServiceImpl implements IQuestionBankService {

    QuestionBankRepository questionBankRepository;

    TestRepository testRepository;

    @Override
    public List<QuestionBank> getAllQuestion() {
        return questionBankRepository.findAll();
    }

    @Override
    public QuestionBank getQuestionById(long questionId) {

        QuestionBank questionBank = questionBankRepository.findById(questionId).orElse(null);

        if(questionBank == null) {
            throw new ResourceNotFoundException("QuestionBank not found with id: " + questionId);
        }

        return questionBank;
    }

    @Override
    public QuestionBank createQuestion(QuestionBank questionBank) {

        String question = questionBank.getQuestion();
        String firstChoice = questionBank.getFirstChoice();
        String secondChoice = questionBank.getSecondChoice();
        String thirdChoice = questionBank.getThirdChoice();
        String fourthChoice = questionBank.getFourthChoice();
        String correctAnswer = questionBank.getCorrectAnswer();
        Long testId = questionBank.getTest().getTestId();

        Optional<Test> testOptional = testRepository.findById(testId);

        if(testOptional.isPresent()) {

            Test test = testOptional.get();

            QuestionBank questionBank1 = new QuestionBank();
            questionBank1.setQuestion(question);
            questionBank1.setFirstChoice(firstChoice);
            questionBank1.setSecondChoice(secondChoice);
            questionBank1.setThirdChoice(thirdChoice);
            questionBank1.setFourthChoice(fourthChoice);
            questionBank1.setCorrectAnswer(correctAnswer);
            questionBank1.setTest(test);

            return questionBankRepository.save(questionBank1);

        } else {
            throw new ResourceNotFoundException("Question Bank can't be added.");
        }
    }

    @Override
    public QuestionBank updateQuestion(Long questionId, QuestionBank questionBankUpdate) {

        Optional<QuestionBank> questionBankOptional = questionBankRepository.findById(questionId);

        if(questionBankOptional.isPresent()) {
            QuestionBank questionBank = questionBankOptional.get();

            questionBank.setQuestion(questionBankUpdate.getQuestion());
            questionBank.setFirstChoice(questionBankUpdate.getFirstChoice());
            questionBank.setSecondChoice(questionBankUpdate.getSecondChoice());
            questionBank.setThirdChoice(questionBankUpdate.getThirdChoice());
            questionBank.setFourthChoice(questionBankUpdate.getFourthChoice());
            questionBank.setCorrectAnswer(questionBankUpdate.getCorrectAnswer());
            questionBank.setTest(questionBankUpdate.getTest());

            return questionBankRepository.save(questionBank);
        } else {
            throw new ResourceNotFoundException("Question Bank not found");
        }
    }

    @Override
    public void deleteQuestion(Long questionId) {
        QuestionBank questionBank = questionBankRepository.findById(questionId).orElseThrow(() -> new ResourceNotFoundException("Question Bank not exist with id:" + questionId));
        questionBankRepository.delete(questionBank);

    }
}
