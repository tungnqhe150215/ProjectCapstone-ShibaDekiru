package com.sep490.g49.shibadekiru.controller.student;

import com.sep490.g49.shibadekiru.dto.GrammarDto;
import com.sep490.g49.shibadekiru.dto.KanjiDto;
import com.sep490.g49.shibadekiru.dto.VocabularyDto;
import com.sep490.g49.shibadekiru.service.IGrammarService;
import com.sep490.g49.shibadekiru.service.IKanjiService;
import com.sep490.g49.shibadekiru.service.IVocabularyService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api")
public class StudentListController {

    ModelMapper modelMapper;

    IKanjiService kanjiService;

    IVocabularyService vocabularyService;

    IGrammarService grammarService;

    @GetMapping("/kanji")
    public List<KanjiDto> getAllKanji() {
        return kanjiService.getAllKanji().stream().map(kanji -> modelMapper.map(kanji, KanjiDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/vocabulary")
    public List<VocabularyDto> getAllVocabulary() {
        return vocabularyService.getAllVocabulary().stream().map(vocabulary -> modelMapper.map(vocabulary, VocabularyDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/grammar")
    public List<GrammarDto> getAllGrammar() {
        return grammarService.getAllGrammar().stream().map(grammar -> modelMapper.map(grammar, GrammarDto.class)).collect(Collectors.toList());
    }

}
