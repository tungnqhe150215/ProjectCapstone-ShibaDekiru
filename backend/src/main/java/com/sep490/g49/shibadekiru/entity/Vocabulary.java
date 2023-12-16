package com.sep490.g49.shibadekiru.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vocabulary")
public class Vocabulary implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vocabulary_id")
    private Long vocabularyId;

    @Column(name = "vocabulary_name", nullable = false)
    private String vocabularyName;

    @Column(name = "hiragana", nullable = false)
    private String hiragana;

    @Column(name = "meaning", nullable = false)
    private String meaning;

    @Column(name = "example", nullable = false , columnDefinition = "TEXT")
    private String example;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "lesson_id", referencedColumnName = "lesson_id")
    private Lesson lesson;


}
