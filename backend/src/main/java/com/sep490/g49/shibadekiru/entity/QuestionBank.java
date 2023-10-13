package com.sep490.g49.shibadekiru.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Entity
@Table(name = "question_bank")
public class QuestionBank implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_bank_id")
    private Long questionBankId;

    private String question;

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


    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "test_id", referencedColumnName = "test_id")
    private Test test;


}
