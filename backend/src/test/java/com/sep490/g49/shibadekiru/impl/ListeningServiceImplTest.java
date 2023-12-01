package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.Lesson;
import com.sep490.g49.shibadekiru.entity.Listening;
import com.sep490.g49.shibadekiru.repository.ListeningRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListeningServiceImplTest {

    @Mock
    private ListeningRepository listeningRepository;

    @InjectMocks
    private ListeningServiceImpl listeningService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getListeningPartByLesson() {
        // Arrange
        Lesson lesson = new Lesson();
        List<Listening> listenings = new ArrayList<>();
        listenings.add(new Listening());
        listenings.add(new Listening());
        when(listeningRepository.findListeningsByLesson(lesson)).thenReturn(listenings);

        // Act
        List<Listening> result = listeningService.getListeningPartByLesson(lesson);

        // Assert
        assertEquals(2, result.size());
        verify(listeningRepository, times(1)).findListeningsByLesson(lesson);
    }

    @Test
    void getListeningById() {
        // Arrange
        Long listeningId = 1L;
        Listening listening = new Listening();
        when(listeningRepository.findListeningByListeningId(listeningId)).thenReturn(listening);

        // Act
        Listening result = listeningService.getListeningById(listeningId);

        // Assert
        assertEquals(listening, result);
    }

    @Test
    void createListening() {
        // Arrange
        Listening listeningRequest = new Listening();
        when(listeningRepository.save(listeningRequest)).thenReturn(listeningRequest);

        // Act
        Listening result = listeningService.createListening(listeningRequest);

        // Assert
        assertNotNull(result);
        assertEquals(listeningRequest, result);
        verify(listeningRepository, times(1)).save(listeningRequest);
    }

    @Test
    void updateListening() {
        // Arrange
        Long listeningId = 1L;
        Listening existingListening = new Listening();
        Listening updatedListening = new Listening();
        updatedListening.setTitle("Updated Title");

        when(listeningRepository.findListeningByListeningId(listeningId)).thenReturn(existingListening);
        when(listeningRepository.save(any())).thenReturn(updatedListening);

        // Act
        Listening result = listeningService.updateListening(listeningId, updatedListening);

        // Assert
        assertNotNull(result);
        assertEquals(updatedListening.getTitle(), result.getTitle());
        verify(listeningRepository, times(1)).findListeningByListeningId(listeningId);
        verify(listeningRepository, times(1)).save(any());
    }

    @Test
    void deleteListening() {
        // Arrange
        Long listeningId = 1L;
        Listening listening = new Listening();
        when(listeningRepository.findListeningByListeningId(listeningId)).thenReturn(listening);

        // Act
        listeningService.deleteListening(listeningId);

        // Assert
        verify(listeningRepository, times(1)).findListeningByListeningId(listeningId);
        verify(listeningRepository, times(1)).delete(listening);
    }
}
