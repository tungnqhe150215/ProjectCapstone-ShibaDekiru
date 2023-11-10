package com.sep490.g49.shibadekiru.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "post")
public class Post implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @Column(name = "post_content", nullable = false, columnDefinition = "TEXT")
    private String postContent;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = true)
    private LocalDateTime createdAt;

    @Column(name = "is_enabled", nullable = false)
    private Boolean isEnabled;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Comment> comment;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "lecture_id", referencedColumnName = "lecture_id")
    private Lectures lecture;

    @JsonProperty
    public Long getLectureId() {
        return this.lecture.getLectureId();
    }
}
