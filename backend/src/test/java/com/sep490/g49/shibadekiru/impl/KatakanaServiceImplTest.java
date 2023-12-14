package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.Katakana;
import com.sep490.g49.shibadekiru.repository.KatakanaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class KatakanaServiceImplTest {
    @Mock
    private KatakanaRepository katakanaRepository;

    @InjectMocks
    private KatakanaServiceImpl katakanaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void getAllKatakana() {
        // Arrange
        List<Katakana> katakanaList = new ArrayList<>();
        katakanaList.add(new Katakana());
        when(katakanaRepository.findAll()).thenReturn(katakanaList);

        // Act
        List<Katakana> result = katakanaService.getAllKatakana();

        // Assert
        Assertions.assertEquals(katakanaList, result);
    }

    @Test
    void getKatakanaById() {
        // Arrange
        Long katakanaId = 1L;
        Katakana katakana = new Katakana();
        when(katakanaRepository.findById(katakanaId)).thenReturn(Optional.of(katakana));

        // Act
        Katakana result = katakanaService.getKatakanaById(katakanaId);

        // Assert
        Assertions.assertEquals(katakana, result);
    }

    @Test
    void createKatakana() {
        // Arrange
        Katakana katakana = new Katakana();
        katakana.setKatakanaCharacter("あ");
        katakana.setRomanji("a");
        when(katakanaRepository.save(katakana)).thenReturn(katakana);

        // Act
        Katakana result = katakanaService.createKatakana(katakana);

        // Assert
        Assertions.assertEquals(katakana, result);
        verify(katakanaRepository, times(1)).save(katakana);
    }

    @Test
    void updateKatakana() {
        // Arrange
        Long katakanaId = 1L;
        Katakana katakana = new Katakana();
        katakana.setKatakanaCharacter("あ");
        katakana.setRomanji("a");

        Katakana katakanaUpdate = new Katakana();
        katakana.setKatakanaCharacter("あ");
        katakana.setRomanji("a");


        when(katakanaRepository.findById(katakanaId)).thenReturn(Optional.of(katakana));
        when(katakanaRepository.save(katakana)).thenReturn(katakana);

        // Act
        Katakana result = katakanaService.updateKatakana(katakanaId, katakanaUpdate);

        // Assert
        Assertions.assertEquals(katakanaUpdate, result);
        verify(katakanaRepository, times(1)).findById(katakanaId);
        verify(katakanaRepository, times(1)).save(katakana);
    }

    @Test
    void deleteKatakana() {
        // Arrange
        Long katakanaId = 1L;
        Katakana katakana = new Katakana();
        when(katakanaRepository.findById(katakanaId)).thenReturn(Optional.of(katakana));

        // Act
        katakanaService.deleteKatakana(katakanaId);

        // Assert
        verify(katakanaRepository, times(1)).findById(katakanaId);
        verify(katakanaRepository, times(1)).delete(katakana);
    }
}