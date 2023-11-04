package com.sep490.g49.shibadekiru.dto;

import com.sep490.g49.shibadekiru.entity.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
    private Long roleId;
    private RoleType roleType;

}
