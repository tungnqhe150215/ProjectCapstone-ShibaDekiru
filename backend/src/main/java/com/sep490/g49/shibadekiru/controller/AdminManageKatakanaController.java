package com.sep490.g49.shibadekiru.controller;

import com.sep490.g49.shibadekiru.dto.HiraganaDto;
import com.sep490.g49.shibadekiru.dto.KatakanaDto;
import com.sep490.g49.shibadekiru.service.IKatakanaService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

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
}
