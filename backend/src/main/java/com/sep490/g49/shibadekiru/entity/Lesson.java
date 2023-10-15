package com.sep490.g49.shibadekiru.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lesson")
public class Lesson implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_id")
    private Long lessonId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(nullable = true)
    private LocalDateTime createdAt;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @Column(name = "image", length = 250)
    private String image;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "book_id", referencedColumnName = "book_id")
    private Book book;
}
