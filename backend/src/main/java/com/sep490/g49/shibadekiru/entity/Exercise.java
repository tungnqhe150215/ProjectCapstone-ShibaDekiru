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
@Table(name = "exercise")
public class Exercise implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exercise_id")
    private Long exerciseId;

    @Column(columnDefinition = "TINY TEXT")
    private String title;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
    private Boolean isDeleted;

    @OneToMany(mappedBy = "exercise")
    @JsonBackReference
    private List<WritingExercise> writingExercise;

    @OneToMany(mappedBy = "exercise")
    @JsonBackReference
    private List<SubmitFileExercise> submitFileExercises;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "class_work_id", referencedColumnName = "class_work_id")
    private ClassWork classWork;



}
