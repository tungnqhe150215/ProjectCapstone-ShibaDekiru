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
@Table(name = "post")
public class Post implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @Column(name = "post_content", nullable = false)
    private String postContent;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(nullable = true)
    private LocalDateTime createdAt;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @OneToMany(mappedBy = "post")
    @JsonBackReference
    private List<Comment> comment;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "lecture_id", referencedColumnName = "lecture_id")
    private Lectures lectures;

}
