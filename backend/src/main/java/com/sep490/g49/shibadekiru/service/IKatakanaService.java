package com.sep490.g49.shibadekiru.service;

import com.sep490.g49.shibadekiru.entity.Katakana;

import java.util.Collection;
import java.util.List;

public interface IKatakanaService {
    List<Katakana> getAllKatakana();

    Katakana getKatakanaById(long katakanaId);

    Katakana createKatakana(Katakana katakana);

    Katakana updateKatakana(Long katakanaId, Katakana katakanaUpdate);

    void deleteKatakana(Long katakanaId);
}
