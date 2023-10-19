package com.sep490.g49.shibadekiru.service;

import com.sep490.g49.shibadekiru.entity.Hiragana;

import java.util.List;

public interface IHiraganaService {
    List<Hiragana> getAllHiragana();

    Hiragana getHiraganaById(long hiraganaId);

    Hiragana createHiragana(Hiragana hiragana);

    Hiragana updateHiragana(Long hiraganaId, Hiragana hiraganaUpdate);

    void deleteHiragana(Long hiraganaId);
}
