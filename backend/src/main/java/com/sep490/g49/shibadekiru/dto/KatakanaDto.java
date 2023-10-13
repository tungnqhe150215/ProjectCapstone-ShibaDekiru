package com.sep490.g49.shibadekiru.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KatakanaDto {
    private Long katakanaId;
    private String katakanaCharacter;
    private String romanji;
}
