package com.sep490.g49.shibadekiru.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Entity
@Table(name = "choice_exercise_answer")
public class ChoiceExerciseAnswer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "choice_exercise_answer_id")
    private Long choiceExerciseAnswerId;

    private String answer;

    private String mark;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "student_id", referencedColumnName = "student_id")
    private Student student;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "multiple_choice_answer_id", referencedColumnName = "multiple_choice_answer_id")
    private MultipleChoiceAnswer multipleChoiceAnswer;
}
