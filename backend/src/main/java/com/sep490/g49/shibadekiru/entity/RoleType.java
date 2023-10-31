package com.sep490.g49.shibadekiru.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleType {
    ADMIN(1,"ADMIN"),
    LECTURE(2,"LECTURE"),
    STUDENT(3,"STUDENT");

    private final int id;
    private final String roleType;

    public static String getRoleTypeById(int id) {
        for (RoleType roleType : RoleType.values()) {
            if (roleType.getId() == id) {
                return roleType.getRoleType();
            }
        }
        return null; // Trong trường hợp không tìm thấy id
    }
}
