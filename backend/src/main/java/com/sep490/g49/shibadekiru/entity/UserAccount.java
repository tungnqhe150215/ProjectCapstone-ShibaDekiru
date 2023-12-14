package com.sep490.g49.shibadekiru.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_account")
public class UserAccount implements UserDetails {

    @Getter
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

    @Column(name = "email", nullable = false, unique = true ,length = 45)
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    private String password;


    @Column(name = "reset_code", nullable = true, length = 350)
    private String resetCode;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "is_banned", nullable = false)
    private Boolean isBanned;

    @Column(name = "is_created_by_admin", nullable = false)
    private Boolean isCreatedByAdmin;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "role_id", nullable = false, referencedColumnName = "role_id")
    private Role role;

    @OneToMany(mappedBy = "userAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Token> tokens;

    @OneToMany(mappedBy = "userAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Comment> comment;

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        if (role != null) {
            RoleType roleType = RoleType.valueOf(RoleType.getRoleTypeById(Math.toIntExact(role.getRoleId())));
            System.out.println("check authourity " + roleType);
            if (roleType != null) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + roleType.getRoleType()));
            }
        }

        return authorities;
    }
}
