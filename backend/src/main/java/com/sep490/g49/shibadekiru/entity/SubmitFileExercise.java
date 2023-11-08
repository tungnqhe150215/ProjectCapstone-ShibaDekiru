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
@Table(name = "file_exercise")
public class SubmitFileExercise implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "file_url")
    private String fileUrl;

    @OneToMany(mappedBy = "submitFileExercise")
    @JsonBackReference
    private List<FileAnswer> fileAnswers;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "exercise_id", referencedColumnName = "exercise_id")
    private Exercise exercise;
}
