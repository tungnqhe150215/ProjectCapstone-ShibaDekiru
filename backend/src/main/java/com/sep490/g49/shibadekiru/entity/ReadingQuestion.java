package com.sep490.g49.shibadekiru.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reading_question")
public class ReadingQuestion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reading_question_id")
    private Long readingQuestionId;

    private String question;

    @Column(name = "sample_answer")
    private String sampleAnswer;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "reading_id", referencedColumnName = "reading_id")
    private Reading reading;
}
