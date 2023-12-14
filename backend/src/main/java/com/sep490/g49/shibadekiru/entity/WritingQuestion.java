package com.sep490.g49.shibadekiru.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "writing_question")
public class WritingQuestion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "writing_question_id")
    private Long writingQuestionId;

    private String question;

    @Column(name = "sample_answer", columnDefinition = "LONGTEXT")
    private String sampleAnswer;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "writing_id", referencedColumnName = "writing_id")
    private Writing writing;

}
