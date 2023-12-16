package com.sep490.g49.shibadekiru.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "test_section")
public class TestSection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "section_id")
    private Long sectionId;

    @Column(length = 255)
    private String sectionName;

    @Enumerated(EnumType.STRING)
    private SectionType sectionType;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
    private Boolean isDeleted;

    @Column(columnDefinition = "TEXT")
    private String sectionAttach;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id",referencedColumnName = "test_id")
    private Test test;

    @OneToMany(mappedBy = "section",cascade = CascadeType.ALL)
    @JsonBackReference
    private List<QuestionBank> questions;

    @OneToMany(mappedBy = "testSection",cascade = CascadeType.ALL)
    @JsonBackReference
    private List<TestResult> testResult;

}
