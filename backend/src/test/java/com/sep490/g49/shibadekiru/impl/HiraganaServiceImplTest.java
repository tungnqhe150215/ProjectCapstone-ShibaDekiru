package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.Hiragana;
import com.sep490.g49.shibadekiru.entity.Katakana;
import com.sep490.g49.shibadekiru.repository.HiraganaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class HiraganaServiceImplTest {
    @InjectMocks
    HiraganaServiceImpl hiraganaService;


    @Mock
    HiraganaRepository hiraganaRepository;

    @BeforeEach
    void setUp() { MockitoAnnotations.initMocks(this); }
    @Test
    void getAllHiragana() {
        // Arrange
        List hiraganaList = new ArrayList<>();
        Hiragana hiragana1 = new Hiragana();
        Hiragana hiragana2 = new Hiragana();
        hiraganaList.add(hiragana1);
        hiraganaList.add(hiragana2);

        when(hiraganaRepository.findAll()).thenReturn(hiraganaList);

        // Act
        List result = hiraganaService.getAllHiragana();

        // Assert
         assertEquals(hiraganaList.size(), result.size());
         assertEquals(hiraganaList.get(0), result.get(0));
         assertEquals(hiraganaList.get(1), result.get(1));
    }

    @Test
    void getHiraganaById() {
        // Arrange
        Long hiraganaId = 1L;
        Hiragana hiragana = new Hiragana();
        when(hiraganaRepository.findById(hiraganaId)).thenReturn(Optional.of(hiragana));

        // Act
        Hiragana result = hiraganaService.getHiraganaById(hiraganaId);

        // Assert
        Assertions.assertEquals(hiragana, result);
    }

    @Test
    void createHiragana() {
        // Arrange
        Hiragana hiragana = new Hiragana();
        hiragana.setHiraganaCharacter("あ");
        hiragana.setRomanji("a");
        when(hiraganaRepository.save(hiragana)).thenReturn(hiragana);

        // Act
        Hiragana result = hiraganaService.createHiragana(hiragana);

        // Assert
        Assertions.assertEquals(hiragana, result);
        verify(hiraganaRepository, times(1)).save(hiragana);
    }

    @Test
    void updateHiragana() {
        // Arrange
        Long hiraganaId = 1L;
        Hiragana hiragana = new Hiragana();
        hiragana.setHiraganaCharacter("あ");
        hiragana.setRomanji("a");
        Hiragana hiraganaUpdate = new Hiragana();
        hiragana.setHiraganaCharacter("い");
        hiragana.setRomanji("i");


        when(hiraganaRepository.findById(hiraganaId)).thenReturn(Optional.of(hiragana));
        when(hiraganaRepository.save(hiragana)).thenReturn(hiragana);

        // Act
        Hiragana result = hiraganaService.updateHiragana(hiraganaId, hiraganaUpdate);

        // Assert
        Assertions.assertEquals(hiraganaUpdate, result);
        verify(hiraganaRepository, times(1)).findById(hiraganaId);
        verify(hiraganaRepository, times(1)).save(hiragana);
    }

    @Test
    void deleteHiragana() {
        // Arrange
        Long hiraganaId = 1L;
        Hiragana hiragana = new Hiragana();
        when(hiraganaRepository.findById(hiraganaId)).thenReturn(Optional.of(hiragana));

        // Act
        hiraganaService.deleteHiragana(hiraganaId);

        // Assert
        verify(hiraganaRepository, times(1)).findById(hiraganaId);
        verify(hiraganaRepository, times(1)).delete(hiragana);
    }
}