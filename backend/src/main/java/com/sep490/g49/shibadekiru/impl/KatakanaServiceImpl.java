package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.Katakana;
import com.sep490.g49.shibadekiru.repository.KatakanaRepository;
import com.sep490.g49.shibadekiru.service.IKatakanaService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KatakanaServiceImpl implements IKatakanaService {

    KatakanaRepository katakanaRepository;
    @Override
    public List<Katakana> getAllKatakana() {
        return katakanaRepository.findAll();
    }
}
