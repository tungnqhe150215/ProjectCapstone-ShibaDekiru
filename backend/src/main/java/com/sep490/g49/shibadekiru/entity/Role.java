package com.sep490.g49.shibadekiru.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "role")
public class Role  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_type")
    private RoleType roleType;

//    private Set<RoleType> roleTypes;
//
//    public List<SimpleGrantedAuthority> getAuthorities() {
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//
//        Set<RoleType> roleTypes = getRoleTypes();
//        if (roleTypes != null) {
//            // Convert RoleType to SimpleGrantedAuthority and add to authorities
//            authorities.addAll(roleTypes.stream()
//                    .map(roleType -> new SimpleGrantedAuthority("ROLE_" + roleType.getRoleType()))
//                    .toList());
//        }
//
//        RoleType currentRoleType = this.roleType;
//        if (currentRoleType != null) {
//            // Add current role's roleType to authorities
//            authorities.add(new SimpleGrantedAuthority("ROLE_" + currentRoleType.getRoleType()));
//        }
//
//        System.out.println("Role  " + authorities.toString());
//
//        return authorities;
//    }



}