package com.sep490.g49.shibadekiru.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "kaiwa")
public class Kaiwa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kaiwa_id")
    private Long kaiwaId;

    private String title;

    private String link;

    private String script;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "lesson_id", referencedColumnName = "lesson_id")
    private Lesson lesson;
}
