package com.sep490.g49.shibadekiru.controller;

import com.sep490.g49.shibadekiru.dto.kanjiDto.KanjiDTOResponse;
import com.sep490.g49.shibadekiru.service.IKanjiService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor //Inject KanjiService vào controller
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/admin/kanji")
public class KanjiManageController {

    IKanjiService kanjiService;

    @GetMapping()
    public List<KanjiDTOResponse> getAllKanji() {
        return kanjiService.getAllKanji();
    }
}
