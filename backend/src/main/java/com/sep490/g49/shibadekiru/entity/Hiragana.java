package com.sep490.g49.shibadekiru.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Entity
@Table(name = "hiragana")
public class Hiragana implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hiragana_id")
    private Long hiraganaId;

    @Column(name = "hiragana_character")
    private String hiraganaCharacter;

    @Column(name = "romaji")
    private String romanji;
}
