package com.sep490.g49.shibadekiru.controller.student;

import com.sep490.g49.shibadekiru.dto.HiraganaDto;
import com.sep490.g49.shibadekiru.dto.KatakanaDto;
import com.sep490.g49.shibadekiru.entity.Hiragana;
import com.sep490.g49.shibadekiru.entity.Katakana;
import com.sep490.g49.shibadekiru.repository.HiraganaRepository;
import com.sep490.g49.shibadekiru.repository.KatakanaRepository;
import com.sep490.g49.shibadekiru.service.IHiraganaService;
import com.sep490.g49.shibadekiru.service.IKatakanaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/alphabet")
public class StudentAlphabetController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IHiraganaService iHiraganaService;

    @Autowired
    private IKatakanaService iKatakanaService;

    @GetMapping("/hiragana")
    public List<HiraganaDto> getAllHiragana() {
        return iHiraganaService.getAllHiragana().stream().map(hiragana -> modelMapper.map(hiragana, HiraganaDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/katakana")
    public List<KatakanaDto> getAllKatakana() {
        return iKatakanaService.getAllKatakana().stream().map(katakana -> modelMapper.map(katakana, KatakanaDto.class)).collect(Collectors.toList());
    }


    @GetMapping("/hiragana/{hiraganaId}")
    public ResponseEntity<HiraganaDto> getHiraganaById(@PathVariable(name = "hiraganaId") long hiraganaId) {
        Hiragana hiragana = iHiraganaService.getHiraganaById(hiraganaId);

        //convert Entity to DTO
        HiraganaDto hiraganaResponse = modelMapper.map(hiragana, HiraganaDto.class);
        return ResponseEntity.ok().body(hiraganaResponse);
    }

    @GetMapping("/katakana/{katakanaId}")
    public ResponseEntity<KatakanaDto> getKatakanaById(@PathVariable(name = "katakanaId") long katakanaId) {
        Katakana katakana = iKatakanaService.getKatakanaById(katakanaId);

        //convert Entity to DTO
        KatakanaDto katakanaResponse = modelMapper.map(katakana, KatakanaDto.class);

        return ResponseEntity.ok().body(katakanaResponse);
    }
}
