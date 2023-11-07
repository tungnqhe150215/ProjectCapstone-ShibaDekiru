package com.sep490.g49.shibadekiru.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(nullable = true)
    private LocalDateTime createdAt;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_account_id", referencedColumnName = "user_account_id", foreignKey = @ForeignKey(name = "fk_comment_user_account_id"))
    private UserAccount userAccount;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "post_id", referencedColumnName = "post_id")
    private Post post;

    @JsonProperty("userAccountId")
    public Long getUserAccountId() {
        return this.userAccount.getUserAccountId();
    }

}
