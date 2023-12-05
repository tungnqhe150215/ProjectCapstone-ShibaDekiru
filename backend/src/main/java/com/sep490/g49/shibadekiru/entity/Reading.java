package com.sep490.g49.shibadekiru.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reading")
public class Reading implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reading_id")
    private Long readingId;

    private String title;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    private String image;

    @OneToMany(mappedBy = "reading")
    @JsonBackReference
    private List<ReadingQuestion> readingQuestion;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "lesson_id", referencedColumnName = "lesson_id")
    private Lesson lesson;


}
