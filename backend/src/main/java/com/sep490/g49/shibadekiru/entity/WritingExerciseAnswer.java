package com.sep490.g49.shibadekiru.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@Entity
@Table(name = "writing_exercise_answer")
public class WritingExerciseAnswer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "writing_exercise_answer_id")
    private Long writingExerciseAnswerId;

    private String answer;

    private String mark;

    @ManyToOne()
    @JsonBackReference
    @JoinColumn(name = "student_id", referencedColumnName = "student_id")
    private Student student;

    @ManyToOne()
    @JsonBackReference
    @JoinColumn(name = "writing_quiz_id", referencedColumnName = "writing_quiz_id")
    private WritingExercise writingExercise;


}
