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
@Table(name = "lecturers")
public class Lectures implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_id")
    private Long lectureId;

    @Column(name = "first_name", nullable = false, length = 45)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 45)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true, length = 45)
    private String email;

    @Column(name = "phone", nullable = false, length = 10)
    private String phone;

    @Column(name = "avatar", length = 250)
    private String avatar;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id", referencedColumnName = "member_id")
    private UserAccount userAccount;

    @OneToMany(mappedBy = "lectures")
    @JsonBackReference
    private List<Post> post;

    @OneToMany(mappedBy = "lectures")
    @JsonBackReference
    private List<Class> aClass;

    @OneToMany(mappedBy = "lectures")
    @JsonBackReference
    private List<Test> test;
}
