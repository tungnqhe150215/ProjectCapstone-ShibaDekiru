package com.sep490.g49.shibadekiru.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "kanji")
public class Kanji implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kanji_id")
    private Long kanjiId;

    @Column(name = "character_kanji")
    private String characterKanji;

    @Column(name = "on_reading")
    private String onReading;

    @Column(name = "kun_reading")
    private String kunReading;

    @Column(name = "chinese_mean")
    private String chineseMean;

    @Column(name = "example")
    private String example;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "lesson_id", referencedColumnName = "lesson_id")
    private Lesson lesson;
}
