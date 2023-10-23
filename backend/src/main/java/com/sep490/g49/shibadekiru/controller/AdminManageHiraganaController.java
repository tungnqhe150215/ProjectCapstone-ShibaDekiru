package com.sep490.g49.shibadekiru.controller;

import com.sep490.g49.shibadekiru.dto.HiraganaDto;
import com.sep490.g49.shibadekiru.dto.KanjiDto;
import com.sep490.g49.shibadekiru.entity.Hiragana;
import com.sep490.g49.shibadekiru.service.IHiraganaService;
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
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/admin/hiragana")
public class AdminManageHiraganaController {

    ModelMapper modelMapper;

    IHiraganaService hiraganaService;

    @GetMapping()
    public List<HiraganaDto> getAllHiragana () {
        return hiraganaService.getAllHiragana().stream().map(hiragana -> modelMapper.map(hiragana, HiraganaDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/{hiraganaId}")
    public ResponseEntity<HiraganaDto> getHiraganaById(@PathVariable(name = "hiraganaId") long hiraganaId) {
        Hiragana hiragana = hiraganaService.getHiraganaById(hiraganaId);

        //convert Entity to DTO
        HiraganaDto hiraganaResponse = modelMapper.map(hiragana, HiraganaDto.class);
        return ResponseEntity.ok().body(hiraganaResponse);
    }

    @PostMapping()
    public ResponseEntity<HiraganaDto> createHiragana(@RequestBody HiraganaDto hiraganaDto) {

        Hiragana hiraganaRequest = modelMapper.map(hiraganaDto, Hiragana.class);

        Hiragana hiragana = hiraganaService.createHiragana(hiraganaRequest);

        HiraganaDto hiraganaReponse = modelMapper.map(hiragana, HiraganaDto.class);

        return new ResponseEntity<HiraganaDto>(hiraganaReponse, HttpStatus.CREATED);
    }

    @PutMapping("/{hiraganaId}")
    public ResponseEntity<HiraganaDto> updateHiragana(@PathVariable Long hiraganaId, @RequestBody HiraganaDto hiraganaDto) {

        Hiragana hiraganaRequest = modelMapper.map(hiraganaDto, Hiragana.class);

        Hiragana hiragana = hiraganaService.updateHiragana(hiraganaId, hiraganaRequest);

        HiraganaDto hiraganaResponse = modelMapper.map(hiragana, HiraganaDto.class);

        return ResponseEntity.ok().body(hiraganaResponse);
    }

    @DeleteMapping("/{hiraganaId}")
    public ResponseEntity<Map<String, Boolean>> deleteHiragana(@PathVariable Long hiraganaId) {
        hiraganaService.deleteHiragana(hiraganaId);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
