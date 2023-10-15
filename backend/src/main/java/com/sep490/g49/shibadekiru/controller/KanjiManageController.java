package com.sep490.g49.shibadekiru.controller;

import com.sep490.g49.shibadekiru.dto.KanjiDto;
import com.sep490.g49.shibadekiru.entity.Kanji;
import com.sep490.g49.shibadekiru.service.IKanjiService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor //Inject KanjiService vào controller
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/admin/kanji")
public class KanjiManageController {

    ModelMapper modelMapper;

    IKanjiService kanjiService;

    @GetMapping()
    public List<KanjiDto> getAllKanji() {
        return kanjiService.getAllKanji().stream().map(kanji -> modelMapper.map(kanji, KanjiDto.class)).collect(Collectors.toList());
    }

    @PostMapping()
    public ResponseEntity<KanjiDto> createKanji(@RequestBody KanjiDto kanjiDto) {

        Kanji kanjiRequest = modelMapper.map(kanjiDto, Kanji.class);

        Kanji kanji = kanjiService.createKanji(kanjiRequest);

        KanjiDto kanjiResponse = modelMapper.map(kanji, KanjiDto.class);

        return new ResponseEntity<KanjiDto>(kanjiResponse, HttpStatus.CREATED);
    }
}
