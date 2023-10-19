package com.sep490.g49.shibadekiru.controller;

import com.sep490.g49.shibadekiru.dto.HiraganaDto;
import com.sep490.g49.shibadekiru.dto.KatakanaDto;
import com.sep490.g49.shibadekiru.entity.Katakana;
import com.sep490.g49.shibadekiru.service.IKatakanaService;
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
@RequestMapping("/api/admin/katakana")
public class AdminManageKatakanaController {

    ModelMapper modelMapper;

    IKatakanaService katakanaService;

    @GetMapping
    public List<KatakanaDto> getAllKatakana() {
        return katakanaService.getAllKatakana().stream().map(katakana -> modelMapper.map(katakana, KatakanaDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/{katakanaId}")
    public ResponseEntity<KatakanaDto> getKatakanaById(@PathVariable(name = "katakanaId") long katakanaId) {
        Katakana katakana = katakanaService.getKatakanaById(katakanaId);

        //convert Entity to DTO
        KatakanaDto katakanaResponse = modelMapper.map(katakana, KatakanaDto.class);

        return ResponseEntity.ok().body(katakanaResponse);
    }

    @PostMapping()
    public ResponseEntity<KatakanaDto> createKatakana(@RequestBody KatakanaDto katakanaDto) {
        Katakana katakanaRequest = modelMapper.map(katakanaDto, Katakana.class);

        Katakana katakana = katakanaService.createKatakana(katakanaRequest);

        KatakanaDto katakanaResponse = modelMapper.map(katakana, KatakanaDto.class);

        return new ResponseEntity<KatakanaDto>(katakanaResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{katakanaId}")
    public ResponseEntity<KatakanaDto> updateKatakana(@PathVariable Long katakanaId, @RequestBody KatakanaDto katakanaDto) {
        Katakana katakanaRequest = modelMapper.map(katakanaDto, Katakana.class);

        Katakana katakana = katakanaService.updateKatakana(katakanaId, katakanaRequest);

        KatakanaDto katakanaResponse = modelMapper.map(katakana, KatakanaDto.class);

        return ResponseEntity.ok().body(katakanaResponse);
    }

    @DeleteMapping("/{katakanaId}")
    public ResponseEntity<Map<String, Boolean>> deleteKatakana(@PathVariable Long katakanaId) {
        katakanaService.deleteKatakana(katakanaId);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
