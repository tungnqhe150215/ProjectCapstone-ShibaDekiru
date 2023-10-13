package com.sep490.g49.shibadekiru.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "listening_question")
public class ListeningQuestion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "listening_question_id")
    private Long listeningQuestionId;

    private String question;

    @Column(name = "first_choice")
    private String firstChoice;

    @Column(name = "second_choice")
    private String secondChoice;

    @Column(name = "third_choice")
    private String thirdChoice;

    @Column(name = "correct_answer")
    private String correctAnswer;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "listening_id", referencedColumnName = "listening_id")
    private Listening listening;
}
