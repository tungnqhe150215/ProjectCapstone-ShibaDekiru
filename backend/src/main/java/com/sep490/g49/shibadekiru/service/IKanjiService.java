package com.sep490.g49.shibadekiru.service;

import com.sep490.g49.shibadekiru.dto.kanjiDto.KanjiDTOResponse;

import java.util.List;

public interface IKanjiService {
    List<KanjiDTOResponse> getAllKanji();
}
