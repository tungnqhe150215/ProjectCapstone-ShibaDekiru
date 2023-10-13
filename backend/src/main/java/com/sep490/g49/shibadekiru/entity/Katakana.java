package com.sep490.g49.shibadekiru.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "katakana")
public class Katakana implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "katakana_id")
    private Long katakanaId;

    @Column(name = "katakana_character")
    private String katakanaCharacter;

    @Column(name = "romanji")
    private String romanji;
}
