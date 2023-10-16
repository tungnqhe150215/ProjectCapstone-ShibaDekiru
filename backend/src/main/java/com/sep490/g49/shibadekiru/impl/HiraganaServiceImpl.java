package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.Hiragana;
import com.sep490.g49.shibadekiru.repository.HiraganaRepository;
import com.sep490.g49.shibadekiru.service.IHiraganaService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HiraganaServiceImpl implements IHiraganaService {

    HiraganaRepository hiraganaRepository;
    @Override
    public List<Hiragana> getAllHiragana() {
        return hiraganaRepository.findAll();
    }
}
