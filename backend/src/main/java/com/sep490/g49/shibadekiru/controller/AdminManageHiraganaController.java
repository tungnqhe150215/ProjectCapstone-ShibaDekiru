package com.sep490.g49.shibadekiru.controller;

import com.sep490.g49.shibadekiru.dto.HiraganaDto;
import com.sep490.g49.shibadekiru.dto.KanjiDto;
import com.sep490.g49.shibadekiru.entity.Hiragana;
import com.sep490.g49.shibadekiru.service.IHiraganaService;
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

//@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/admin/hiragana")
public class AdminManageHiraganaController {

    ModelMapper modelMapper;

    IHiraganaService hiraganaService;

    @GetMapping
    public List<HiraganaDto> getAllHiragana () {
        return hiraganaService.getAllHiragana().stream().map(hiragana -> modelMapper.map(hiragana, HiraganaDto.class)).collect(Collectors.toList());
    }
}
