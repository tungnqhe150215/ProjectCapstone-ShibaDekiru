package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.QuestionBank;
import com.sep490.g49.shibadekiru.entity.Test;
import com.sep490.g49.shibadekiru.entity.TestSection;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.repository.QuestionBankRepository;
import com.sep490.g49.shibadekiru.repository.TestSectionRepository;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class QuestionBankServiceImplTest {

    @Mock
    private QuestionBankRepository questionBankRepository;

    @Mock
    private TestSectionRepository testSectionRepository;

    @InjectMocks
    private QuestionBankServiceImpl questionBankService;

    @org.junit.jupiter.api.Test
    void getAllQuestionByTestSection() {
        // Mocking the behavior of questionBankRepository.findBySection() method
        TestSection testSection = new TestSection();
        List<QuestionBank> mockQuestions = new ArrayList<>();
        Mockito.when(questionBankRepository.findBySection(testSection)).thenReturn(mockQuestions);

        List<QuestionBank> result = questionBankService.getAllQuestionByTestSection(testSection);

        assertNotNull(result);
        assertEquals(0, result.size()); // Assuming the mockQuestions list is empty
    }

    @org.junit.jupiter.api.Test
    void getQuestionById() {
        // Mocking the behavior of questionBankRepository.findById() method
        Long questionId = 1L;
        QuestionBank expectedQuestion = new QuestionBank();
        Mockito.when(questionBankRepository.findById(questionId)).thenReturn(Optional.of(expectedQuestion));

        QuestionBank result = questionBankService.getQuestionById(questionId);

        assertNotNull(result);
        // Add more assertions as needed
    }

    @org.junit.jupiter.api.Test
    void getQuestionByTest() {
        // Mocking the behavior of questionBankRepository.findBySection() method
        Test test = new Test();
        TestSection testSection = new TestSection();
        testSection.setTest(test);
        List<QuestionBank> mockQuestions = new ArrayList<>();
        Mockito.when(testSectionRepository.findTestSectionsByTest(test)).thenReturn(List.of(testSection));
        Mockito.when(questionBankRepository.findBySection(testSection)).thenReturn(mockQuestions);

        List<QuestionBank> result = questionBankService.getQuestionByTest(test);

        assertNotNull(result);
        assertEquals(0, result.size()); // Assuming the mockQuestions list is empty
    }

    @org.junit.jupiter.api.Test
    void createQuestion() {
        // Mocking the behavior of testSectionRepository.findById() method
        Long sectionId = 1L;
        TestSection testSection = new TestSection();
        Mockito.when(testSectionRepository.findById(sectionId)).thenReturn(Optional.of(testSection));

        // Mocking the behavior of questionBankRepository.save() method
        QuestionBank savedQuestion = new QuestionBank();
        Mockito.when(questionBankRepository.save(any(QuestionBank.class))).thenReturn(savedQuestion);

        QuestionBank result = questionBankService.createQuestion(new QuestionBank());

        assertNotNull(result);
        // Add more assertions as needed
    }

    @org.junit.jupiter.api.Test
    void updateQuestion() {
        Long questionId = 1L;
        QuestionBank updatedQuestion = new QuestionBank();

        // Mocking the behavior of questionBankRepository.findById() method
        Optional<QuestionBank> existingQuestion = Optional.of(new QuestionBank());
        Mockito.when(questionBankRepository.findById(questionId)).thenReturn(existingQuestion);

        // Mocking the behavior of questionBankRepository.save() method
        QuestionBank savedQuestion = new QuestionBank();
        Mockito.when(questionBankRepository.save(any(QuestionBank.class))).thenReturn(savedQuestion);

        QuestionBank result = questionBankService.updateQuestion(questionId, updatedQuestion);

        assertNotNull(result);
        // Add more assertions as needed
    }

    @org.junit.jupiter.api.Test
    void deleteQuestion() {
        Long questionId = 1L;

        // Mocking the behavior of questionBankRepository.findById() method
        Optional<QuestionBank> existingQuestion = Optional.of(new QuestionBank());
        Mockito.when(questionBankRepository.findById(questionId)).thenReturn(existingQuestion);

        assertDoesNotThrow(() -> questionBankService.deleteQuestion(questionId));
    }
}
