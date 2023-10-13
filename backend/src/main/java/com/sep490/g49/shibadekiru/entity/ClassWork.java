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
@Table(name = "class_work")
public class ClassWork implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_work_id")
    private Long classWorkId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(nullable = true)
    private LocalDateTime createdAt;

    @Column(nullable = true)
    private LocalDateTime deadline;

    @Column(name = "is_locked", nullable = false)
    private Boolean isLocked;

    @OneToMany(mappedBy = "classWork")
    @JsonBackReference
    private List<StudentClassWork> studentClassWork;

    @OneToMany(mappedBy = "classWork")
    @JsonBackReference
    private List<Exercise> exercise;


}
