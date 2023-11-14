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

    @Column(columnDefinition = "LONGTEXT")
    private String sectionAttach; // URL cho tệp đính kèm

    // Mô hình quan hệ với bảng Test (Một phần thuộc về một bài test)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id",referencedColumnName = "test_id")
    private Test test;

    // Mô hình quan hệ với bảng QuestionBank (Nhiều câu hỏi trong một phần)
    @OneToMany(mappedBy = "section",cascade = CascadeType.ALL)
    @JsonBackReference
    private List<QuestionBank> questions;

    @OneToMany(mappedBy = "testSection",cascade = CascadeType.ALL)
    @JsonBackReference
    private List<TestResult> testResult;

}
