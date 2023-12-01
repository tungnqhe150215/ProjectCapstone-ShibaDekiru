package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.Listening;
import com.sep490.g49.shibadekiru.entity.ListeningQuestion;
import com.sep490.g49.shibadekiru.repository.ListeningQuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListeningQuestionServiceImplTest {

    @Mock
    private ListeningQuestionRepository listeningQuestionRepository;

    @InjectMocks
    private ListeningQuestionServiceImpl listeningQuestionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getListeningQuestionByListening() {
        // Arrange
        Listening listening = new Listening();
        List<ListeningQuestion> listeningQuestions = new ArrayList<>();
        listeningQuestions.add(new ListeningQuestion());
        listeningQuestions.add(new ListeningQuestion());
        when(listeningQuestionRepository.findByListening(listening)).thenReturn(listeningQuestions);

        // Act
        List<ListeningQuestion> result = listeningQuestionService.getListeningQuestionByListening(listening);

        // Assert
        assertEquals(2, result.size());
        verify(listeningQuestionRepository, times(1)).findByListening(listening);
    }

    @Test
    void getListeningQuestionById() {
        // Arrange
        Long questionId = 1L;
        ListeningQuestion listeningQuestion = new ListeningQuestion();
        when(listeningQuestionRepository.findListeningQuestionByListeningQuestionId(questionId)).thenReturn(listeningQuestion);

        // Act
        ListeningQuestion result = listeningQuestionService.getListeningQuestionById(questionId);

        // Assert
        assertEquals(listeningQuestion, result);
    }

    @Test
    void createListeningQuestion() {
        // Arrange
        ListeningQuestion listeningQuestionRequest = new ListeningQuestion();
        when(listeningQuestionRepository.save(listeningQuestionRequest)).thenReturn(listeningQuestionRequest);

        // Act
        ListeningQuestion result = listeningQuestionService.createListeningQuestion(listeningQuestionRequest);

        // Assert
        assertNotNull(result);
        assertEquals(listeningQuestionRequest, result);
        verify(listeningQuestionRepository, times(1)).save(listeningQuestionRequest);
    }

    @Test
    void updateListeningQuestion() {
        // Arrange
        Long questionId = 1L;
        ListeningQuestion existingQuestion = new ListeningQuestion();
        ListeningQuestion updatedQuestion = new ListeningQuestion();
        updatedQuestion.setQuestion("Updated Question");

        when(listeningQuestionRepository.findListeningQuestionByListeningQuestionId(questionId)).thenReturn(existingQuestion);
        when(listeningQuestionRepository.save(any())).thenReturn(updatedQuestion);

        // Act
        ListeningQuestion result = listeningQuestionService.updateListeningQuestion(questionId, updatedQuestion);

        // Assert
        assertNotNull(result);
        assertEquals(updatedQuestion.getQuestion(), result.getQuestion());
        verify(listeningQuestionRepository, times(1)).findListeningQuestionByListeningQuestionId(questionId);
        verify(listeningQuestionRepository, times(1)).save(any());
    }

    @Test
    void deleteListeningQuestion() {
        // Arrange
        Long questionId = 1L;
        ListeningQuestion listeningQuestion = new ListeningQuestion();
        when(listeningQuestionRepository.findListeningQuestionByListeningQuestionId(questionId)).thenReturn(listeningQuestion);

        // Act
        listeningQuestionService.deleteListeningQuestion(questionId);

        // Assert
        verify(listeningQuestionRepository, times(1)).findListeningQuestionByListeningQuestionId(questionId);
        verify(listeningQuestionRepository, times(1)).delete(listeningQuestion);
    }
}
