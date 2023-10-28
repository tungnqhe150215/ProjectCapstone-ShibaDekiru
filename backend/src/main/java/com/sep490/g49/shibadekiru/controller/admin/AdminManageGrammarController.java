package com.sep490.g49.shibadekiru.controller.admin;

import com.sep490.g49.shibadekiru.dto.GrammarDto;
import com.sep490.g49.shibadekiru.entity.Grammar;
import com.sep490.g49.shibadekiru.service.IGrammarService;
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
@RequestMapping("/api/admin/grammar")
public class AdminManageGrammarController {

    ModelMapper modelMapper;

    IGrammarService grammarService;

    @GetMapping()
    public List<GrammarDto> getAllGrammar() {
        return grammarService.getAllGrammar().stream().map(grammar -> modelMapper.map(grammar, GrammarDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/{grammarId}")
    public ResponseEntity<GrammarDto> getGrammarById(@PathVariable(name = "grammarId") long grammarId) {
        Grammar grammar = grammarService.getGrammarById(grammarId);

        //convert Entity to DTO
        GrammarDto grammarResponse = modelMapper.map(grammar, GrammarDto.class);

        return ResponseEntity.ok().body(grammarResponse);
    }

    @PostMapping()
    public ResponseEntity<GrammarDto> createGrammar(@RequestBody GrammarDto grammarDto) {

        Grammar grammarRequest = modelMapper.map(grammarDto, Grammar.class);

        Grammar grammar = grammarService.createGrammar(grammarRequest);

        GrammarDto grammarResponse = modelMapper.map(grammar, GrammarDto.class);

        return new ResponseEntity<GrammarDto>(grammarResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{grammarId}")
    public ResponseEntity<GrammarDto> updateGrammar(@PathVariable Long grammarId, @RequestBody GrammarDto grammarDto) {

        Grammar grammarRequest = modelMapper.map(grammarDto, Grammar.class);

        Grammar grammar = grammarService.updateGrammar(grammarId, grammarRequest);

        GrammarDto grammarResponse = modelMapper.map(grammar, GrammarDto.class);

        return ResponseEntity.ok().body(grammarResponse);
    }

    @DeleteMapping("/{grammarId}")
    public ResponseEntity<Map<String, Boolean>> deleteGrammar(@PathVariable Long grammarId) {
        grammarService.deleteGrammar(grammarId);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

}
