package com.sep490.g49.shibadekiru.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class HiraganaDto {
    private Long hiraganaId;
    private String hiraganaCharacter;
    private String romanji;
}
