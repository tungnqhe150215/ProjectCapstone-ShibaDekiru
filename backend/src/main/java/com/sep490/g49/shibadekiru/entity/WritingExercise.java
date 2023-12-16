package com.sep490.g49.shibadekiru.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "writing_exercise")
public class WritingExercise implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "writing_quiz_id")
    private Long writingQuizId;

    private String question;

    private Double mark;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
    private Boolean isDeleted;

    @OneToMany(mappedBy = "writingExercise")
    @JsonBackReference
    private List<WritingExerciseAnswer> writingExerciseAnswer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "exercise_id", referencedColumnName = "exercise_id")
    private Exercise exercise;
}
