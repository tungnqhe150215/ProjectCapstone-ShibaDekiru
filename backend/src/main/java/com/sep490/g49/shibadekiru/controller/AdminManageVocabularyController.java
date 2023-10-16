package com.sep490.g49.shibadekiru.controller;

import com.sep490.g49.shibadekiru.dto.VocabularyDto;
import com.sep490.g49.shibadekiru.entity.Vocabulary;
import com.sep490.g49.shibadekiru.service.IVocabularyService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor //Inject KanjiService vào controller
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/admin/vocabulary")
public class AdminManageVocabularyController {

    ModelMapper modelMapper;

    IVocabularyService vocabularyService;

    @GetMapping()
    public List<VocabularyDto> getAllVocabulary() {
        return vocabularyService.getAllVocabulary().stream().map(vocabulary -> modelMapper.map(vocabulary, VocabularyDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/{vocabularyId}")
    public ResponseEntity<VocabularyDto> getVocabularyById(@PathVariable (name = "vocabularyId") long vocabularyId){
        Vocabulary vocabulary = vocabularyService.getVocabularyById(vocabularyId);

        //convert Entity to DTO
        VocabularyDto vocabularyResponse = modelMapper.map(vocabulary, VocabularyDto.class);

        return ResponseEntity.ok().body(vocabularyResponse);
    }

    @PostMapping()
    public ResponseEntity<VocabularyDto> createVocabulary(@RequestBody VocabularyDto vocabularyDto) {

        Vocabulary vocabularyRequest = modelMapper.map(vocabularyDto, Vocabulary.class);

        Vocabulary vocabulary = vocabularyService.createVocabulary(vocabularyRequest);

        VocabularyDto vocabularyResponse = modelMapper.map(vocabulary, VocabularyDto.class);

        return new ResponseEntity<VocabularyDto>(vocabularyResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{vocabularyId}")
    public ResponseEntity<VocabularyDto> updateVocabulary(@PathVariable Long vocabularyId, @RequestBody VocabularyDto vocabularyDto){

        Vocabulary vocabularyRequest = modelMapper.map(vocabularyDto, Vocabulary.class);

        Vocabulary vocabulary = vocabularyService.updateVocabulary(vocabularyId, vocabularyRequest);

        VocabularyDto vocabularyResponse = modelMapper.map(vocabulary, VocabularyDto.class);

        return ResponseEntity.ok().body(vocabularyResponse);
    }

    @DeleteMapping("/{vocabularyId}")
    public ResponseEntity<Map<String, Boolean>> deleteVocabulary(@PathVariable Long vocabularyId) {
        vocabularyService.deleteVocabulary(vocabularyId);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
