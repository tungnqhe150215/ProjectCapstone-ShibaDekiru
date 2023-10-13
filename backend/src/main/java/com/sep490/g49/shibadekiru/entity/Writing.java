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
@Table(name = "writing")
public class Writing implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "writing_id")
    private Long writingId;

    private String topic;

    @OneToMany(mappedBy = "writing")
    @JsonBackReference
    private List<WritingQuestion> writingQuestion;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "lesson_id", referencedColumnName = "lesson_id")
    private Lesson lesson;
}
