package com.sep490.g49.shibadekiru.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Table(name = "test_result")
public class TestResult implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_result_id")
    private Long testResultId;

    @Column(name = "result", nullable = false)
    private Double result;

    @Column(nullable = true)
    private LocalDateTime doneTime;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "student_id", referencedColumnName = "student_id")
    private Student student;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "test_id", referencedColumnName = "test_id")
    private Test test;
}
