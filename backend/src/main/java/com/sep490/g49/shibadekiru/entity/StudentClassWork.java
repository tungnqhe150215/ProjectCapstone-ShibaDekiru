package com.sep490.g49.shibadekiru.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "student_class_work")
public class StudentClassWork implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_class_work_id")
    private Long studentClassWorkId;

    private Double result;

    @Column(nullable = true)
    private LocalDateTime submitTime;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "student_id", referencedColumnName = "student_id")
    private Student student;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "class_work_id", referencedColumnName = "class_work_id")
    private ClassWork classWork;


}
