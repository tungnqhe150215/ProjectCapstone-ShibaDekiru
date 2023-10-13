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
@Table(name = "multiple_choice_answer")
public class MultipleChoiceAnswer implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "multiple_choice_answer_id")
    private Long multipleChoiceAnswerId;

    @Column(name = "first_choice")
    private String firstChoice;

    @Column(name = "second_choice")
    private String secondChoice;

    @Column(name = "third_choice")
    private String thirdChoice;

    @Column(name = "fourth_choice")
    private String fourthChoice;

    @Column(name = "correct_answer")
    private String correctAnswer;

    @OneToMany(mappedBy = "multipleChoiceAnswer")
    @JsonBackReference
    private List<ChoiceExerciseAnswer> choiceExerciseAnswer;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "exercise_id", referencedColumnName = "exercise_id")
    private Exercise exercise;
}
