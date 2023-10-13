package com.sep490.g49.shibadekiru.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "grammar")
public class Grammar implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grammar_id")
    private Long grammarId;

    @Column(name = "grammar_name", nullable = false)
    private String grammarName;

    @Column(name = "grammar_structure", nullable = false)
    private String grammarStructure;

    private String description;

    private String example;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "lesson_id", referencedColumnName = "lesson_id")
    private Lesson lesson;

}
