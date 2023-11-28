package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.Lesson;
import com.sep490.g49.shibadekiru.entity.Listening;
import com.sep490.g49.shibadekiru.repository.ListeningRepository;
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
class ListeningServiceImplTest {

    @Mock
    private ListeningRepository listeningRepository;

    @InjectMocks
    private ListeningServiceImpl listeningService;

    @Test
    void getListeningPartByLesson() {
        Lesson lesson = new Lesson();
        List<Listening> mockListenings = new ArrayList<>();
        Mockito.when(listeningRepository.findListeningsByLesson(lesson)).thenReturn(mockListenings);

        List<Listening> result = listeningService.getListeningPartByLesson(lesson);

        assertNotNull(result);
        assertEquals(0, result.size()); // Assuming the mockListenings list is empty
    }

    @Test
    void getListeningById() {
        Long listeningId = 1L;
        Listening expectedListening = new Listening();
        expectedListening.setListeningId(listeningId);
        Mockito.when(listeningRepository.findListeningByListeningId(listeningId)).thenReturn(expectedListening);

        Listening result = listeningService.getListeningById(listeningId);

        assertNotNull(result);
        assertEquals(listeningId, result.getListeningId());
    }

    @Test
    void createListening() {
        Listening inputListening = new Listening();
        Mockito.when(listeningRepository.save(any(Listening.class))).thenReturn(inputListening);

        Listening result = listeningService.createListening(inputListening);

        assertNotNull(result);
        // Add more assertions as needed
    }

    @Test
    void updateListening() {
        Long listeningId = 1L;
        Listening existingListening = new Listening();
        existingListening.setListeningId(listeningId);

        Listening updatedListening = new Listening();
        updatedListening.setLink("Updated Link");

        Mockito.when(listeningRepository.findListeningByListeningId(listeningId))
                .thenReturn(existingListening);
        Mockito.when(listeningRepository.save(any(Listening.class))).thenReturn(updatedListening);

        Listening result = listeningService.updateListening(listeningId, updatedListening);

        assertNotNull(result);
        assertEquals("Updated Link", result.getLink());
        // Add more assertions as needed
    }

    @Test
    void deleteListening() {
        Long listeningId = 1L;
        Listening existingListening = new Listening();
        existingListening.setListeningId(listeningId);

        Mockito.when(listeningRepository.findListeningByListeningId(listeningId))
                .thenReturn(existingListening);

        assertDoesNotThrow(() -> listeningService.deleteListening(listeningId));
    }
}
