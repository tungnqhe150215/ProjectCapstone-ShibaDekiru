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
@Table(name = "user_account")
public class UserAccount implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_account_id")
    private Long userAccountId;

    @Column(name = "nick_name", nullable = false, length = 45)
    private String nickName;

    @Column(name = "member_id", unique = true)
    private String memberId;


    @Column(name = "username", nullable = false, length = 45)
    private String userName;

    @Column(name = "password", nullable = false, length = 45)
    private String password;

    @Column(name = "email", nullable = false, unique = true ,length = 45)
    private String email;

    @Column(name = "reset_code", nullable = true)
    private String resetCode;

    @Column(name = "is_banned", nullable = false)
    private Boolean isBanned;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "role_id", nullable = false, referencedColumnName = "role_id")
    private Role role;

}
