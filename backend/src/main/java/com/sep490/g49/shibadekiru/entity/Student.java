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
@Table(name = "student")
public class Student implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "first_name", nullable = false, length = 45)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 45)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true, length = 45)
    private String email;

    @Column(name = "gender", nullable = false)
    private Boolean gender;

    @Column(name = "avatar", length = 250)
    private String avatar;


    @OneToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    @JoinColumn(name = "member_id", referencedColumnName = "member_id" )
    private UserAccount userAccount;


    @OneToMany(mappedBy = "student")
    @JsonBackReference
    private List<TestResult> testResult;

    @OneToMany(mappedBy = "student")
    @JsonBackReference
    private List<ClassStudent> classStudent;

    @OneToMany(mappedBy = "student")
    @JsonBackReference
    private List<StudentClassWork> studentClassWork;

    @OneToMany(mappedBy = "student")
    @JsonBackReference
    private List<WritingExerciseAnswer> writingExerciseAnswer;

    @OneToMany(mappedBy = "student")
    @JsonBackReference
    private List<ChoiceExerciseAnswer> choiceExerciseAnswer;

}
