package com.sep490.g49.shibadekiru.controller.student;

import com.sep490.g49.shibadekiru.dto.GrammarDto;
import com.sep490.g49.shibadekiru.dto.KanjiDto;
import com.sep490.g49.shibadekiru.dto.VocabularyDto;
import com.sep490.g49.shibadekiru.entity.Grammar;
import com.sep490.g49.shibadekiru.entity.Kanji;
import com.sep490.g49.shibadekiru.entity.Vocabulary;
import com.sep490.g49.shibadekiru.service.IGrammarService;
import com.sep490.g49.shibadekiru.service.IKanjiService;
import com.sep490.g49.shibadekiru.service.IVocabularyService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/knowledge")
public class StudentListController {

    ModelMapper modelMapper;

    IKanjiService kanjiService;

    IVocabularyService vocabularyService;

    IGrammarService grammarService;

    @GetMapping("/kanji")
    public List<KanjiDto> getAllKanji() {
        return kanjiService.getAllKanji().stream().map(kanji -> modelMapper.map(kanji, KanjiDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/kanji/{kanjiId}")
    public ResponseEntity<KanjiDto> getKanjiById(@PathVariable(name = "kanjiId") long kanjiId){
        Kanji kanji = kanjiService.getKanjiById(kanjiId);
        KanjiDto kanjiResponse = modelMapper.map(kanji, KanjiDto.class);

        return ResponseEntity.ok().body(kanjiResponse);
    }

    @GetMapping("/vocabulary")
    public List<VocabularyDto> getAllVocabulary() {
        return vocabularyService.getAllVocabulary().stream().map(vocabulary -> modelMapper.map(vocabulary, VocabularyDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/vocabulary/{vocabularyId}")
    public ResponseEntity<VocabularyDto> getVocabById(@PathVariable (name = "vocabularyId") long vocabularyId){
        Vocabulary vocabulary = vocabularyService.getVocabularyById(vocabularyId);

        //convert Entity to DTO
        VocabularyDto vocabularyResponse = modelMapper.map(vocabulary, VocabularyDto.class);

        return ResponseEntity.ok().body(vocabularyResponse);
    }

    @GetMapping("/grammar")
    public List<GrammarDto> getAllGrammar() {
        return grammarService.getAllGrammar().stream().map(grammar -> modelMapper.map(grammar, GrammarDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/grammar/{grammarId}")
    public ResponseEntity<GrammarDto> getGrammarById(@PathVariable(name = "grammarId") long grammarId){
        Grammar grammar = grammarService.getGrammarById(grammarId);

        //convert Entity to DTO
        GrammarDto grammarResponse = modelMapper.map(grammar, GrammarDto.class);

        return ResponseEntity.ok().body(grammarResponse);
    }

}
