package com.sep490.g49.shibadekiru.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Table(name = "test")
public class Test implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_id")
    private Long testId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(nullable = true)
    private LocalDateTime createdAt;

    private Long duration;

    @Column(name = "is_locked", nullable = false)
    private Boolean isLocked;

    @OneToMany(mappedBy = "test")
    @JsonBackReference
    private List<TestResult> testResult;

    @OneToMany(mappedBy = "test")
    @JsonBackReference
    private List<QuestionBank> questionBank;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "lecture_id", referencedColumnName = "lecture_id")
    private Lectures lecture;
}
