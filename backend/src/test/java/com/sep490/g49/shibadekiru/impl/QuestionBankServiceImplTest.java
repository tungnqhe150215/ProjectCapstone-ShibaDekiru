package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.QuestionBank;
import com.sep490.g49.shibadekiru.entity.Test;
import com.sep490.g49.shibadekiru.entity.TestSection;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.impl.QuestionBankServiceImpl;
import com.sep490.g49.shibadekiru.repository.QuestionBankRepository;
import com.sep490.g49.shibadekiru.repository.TestSectionRepository;
import org.junit.jupiter.api.BeforeEach;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class QuestionBankServiceImplTest {

    @Mock
    private QuestionBankRepository questionBankRepository;

    @Mock
    private TestSectionRepository testSectionRepository;

    @InjectMocks
    private QuestionBankServiceImpl questionBankService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @org.junit.jupiter.api.Test
    void getAllQuestionByTestSection_ReturnsListOfQuestions() {
        // Arrange
        TestSection testSection = new TestSection();
        List<QuestionBank> mockQuestions = new ArrayList<>();
        when(questionBankRepository.findBySection(testSection)).thenReturn(mockQuestions);

        // Act
        List<QuestionBank> result = questionBankService.getAllQuestionByTestSection(testSection);

        // Assert
        assertThat(result).isSameAs(mockQuestions);
    }

    @org.junit.jupiter.api.Test
    void getQuestionById_WithExistingQuestion_ReturnsQuestion() {
        // Arrange
        Long questionId = 1L;
        QuestionBank existingQuestion = new QuestionBank();
        when(questionBankRepository.findById(questionId)).thenReturn(Optional.of(existingQuestion));

        // Act
        QuestionBank result = questionBankService.getQuestionById(questionId);

        // Assert
        assertThat(result).isSameAs(existingQuestion);
    }

    @org.junit.jupiter.api.Test
    void getQuestionById_WithNonexistentQuestion_ThrowsResourceNotFoundException() {
        // Arrange
        Long questionId = 1L;
        when(questionBankRepository.findById(questionId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> questionBankService.getQuestionById(questionId))
                .isInstanceOf(ResourceNotFoundException.class);
    }





    @org.junit.jupiter.api.Test
    void createQuestion_WithInvalidSection_ThrowsResourceNotFoundException() {
        // Arrange
        QuestionBank inputQuestion = new QuestionBank();
        inputQuestion.setSection(new TestSection());  // Section with no ID
        when(testSectionRepository.findById(null)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> questionBankService.createQuestion(inputQuestion))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @org.junit.jupiter.api.Test
    void updateQuestion_WithExistingQuestion_ReturnsUpdatedQuestion() {
        // Arrange
        Long questionId = 1L;
        QuestionBank updatedQuestion = new QuestionBank();
        updatedQuestion.setQuestion("Updated Question");
        Optional<QuestionBank> existingQuestion = Optional.of(new QuestionBank());
        when(questionBankRepository.findById(questionId)).thenReturn(existingQuestion);
        when(questionBankRepository.save(any())).thenReturn(updatedQuestion);

        // Act
        QuestionBank result = questionBankService.updateQuestion(questionId, updatedQuestion);

        // Assert
        assertThat(result).isSameAs(updatedQuestion);
    }

    @org.junit.jupiter.api.Test
    void updateQuestion_WithNonexistentQuestion_ThrowsResourceNotFoundException() {
        // Arrange
        Long questionId = 1L;
        QuestionBank updatedQuestion = new QuestionBank();
        when(questionBankRepository.findById(questionId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> questionBankService.updateQuestion(questionId, updatedQuestion))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @org.junit.jupiter.api.Test
    void deleteQuestion_WithExistingQuestion_DeletesQuestion() {
        // Arrange
        Long questionId = 1L;
        QuestionBank existingQuestion = new QuestionBank();
        when(questionBankRepository.findById(questionId)).thenReturn(Optional.of(existingQuestion));

        // Act
        questionBankService.deleteQuestion(questionId);

        // Assert
        verify(questionBankRepository, times(1)).delete(existingQuestion);
    }

    @org.junit.jupiter.api.Test
    void deleteQuestion_WithNonexistentQuestion_ThrowsResourceNotFoundException() {
        // Arrange
        Long questionId = 1L;
        when(questionBankRepository.findById(questionId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> questionBankService.deleteQuestion(questionId))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
