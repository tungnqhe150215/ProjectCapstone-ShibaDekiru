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
@Table(name = "class")
public class Class implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id")
    private Long classId;

    @Column(name = "class_name", nullable = false)
    private String className;

    @Column(name = "class_code", nullable = false)
    private String classCode;

    @Column(name = "is_locked", nullable = false)
    private Boolean isLocked;


    @OneToMany(mappedBy = "aclass")
    @JsonBackReference
    private List<ClassStudent> classStudent;

    @ManyToOne()
    @JsonBackReference
    @JoinColumn(name = "lecture_id", referencedColumnName = "lecture_id")
    private Lectures lectures;


}
