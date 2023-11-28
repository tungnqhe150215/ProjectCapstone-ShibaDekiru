package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.Listening;
import com.sep490.g49.shibadekiru.entity.ListeningQuestion;
import com.sep490.g49.shibadekiru.repository.ListeningQuestionRepository;
import org.junit.jupiter.api.Test;
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
class ListeningQuestionServiceImplTest {

    @Mock
    private ListeningQuestionRepository listeningQuestionRepository;

    @InjectMocks
    private ListeningQuestionServiceImpl listeningQuestionService;

    @Test
    void getListeningQuestionByListening() {
        Listening listening = new Listening();
        List<ListeningQuestion> mockQuestions = new ArrayList<>();
        Mockito.when(listeningQuestionRepository.findByListening(listening)).thenReturn(mockQuestions);

        List<ListeningQuestion> result = listeningQuestionService.getListeningQuestionByListening(listening);

        assertNotNull(result);
        assertEquals(0, result.size()); // Assuming the mockQuestions list is empty
    }

    @Test
    void getListeningQuestionById() {
        Long questionId = 1L;
        ListeningQuestion expectedQuestion = new ListeningQuestion();
        expectedQuestion.setListeningQuestionId(questionId);
        Mockito.when(listeningQuestionRepository.findListeningQuestionByListeningQuestionId(questionId))
                .thenReturn(expectedQuestion);

        ListeningQuestion result = listeningQuestionService.getListeningQuestionById(questionId);

        assertNotNull(result);
        assertEquals(questionId, result.getListeningQuestionId());
    }

    @Test
    void createListeningQuestion() {
        ListeningQuestion inputQuestion = new ListeningQuestion();
        Mockito.when(listeningQuestionRepository.save(any(ListeningQuestion.class))).thenReturn(inputQuestion);

        ListeningQuestion result = listeningQuestionService.createListeningQuestion(inputQuestion);

        assertNotNull(result);
        // Add more assertions as needed
    }

    @Test
    void updateListeningQuestion() {
        Long questionId = 1L;
        ListeningQuestion existingQuestion = new ListeningQuestion();
        existingQuestion.setListeningQuestionId(questionId);

        ListeningQuestion updatedQuestion = new ListeningQuestion();
        updatedQuestion.setQuestion("Updated Question");

        Mockito.when(listeningQuestionRepository.findListeningQuestionByListeningQuestionId(questionId))
                .thenReturn(existingQuestion);
        Mockito.when(listeningQuestionRepository.save(any(ListeningQuestion.class))).thenReturn(updatedQuestion);

        ListeningQuestion result = listeningQuestionService.updateListeningQuestion(questionId, updatedQuestion);

        assertNotNull(result);
        assertEquals("Updated Question", result.getQuestion());
        // Add more assertions as needed
    }

    @Test
    void deleteListeningQuestion() {
        Long questionId = 1L;
        ListeningQuestion existingQuestion = new ListeningQuestion();
        existingQuestion.setListeningQuestionId(questionId);

        Mockito.when(listeningQuestionRepository.findListeningQuestionByListeningQuestionId(questionId))
                .thenReturn(existingQuestion);

        assertDoesNotThrow(() -> listeningQuestionService.deleteListeningQuestion(questionId));
    }
}
