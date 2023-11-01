package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.Role;
import com.sep490.g49.shibadekiru.repository.RoleRepository;
import com.sep490.g49.shibadekiru.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Role getByRoleId(Long roleId) {

        return roleRepository.findRoleByRoleId(roleId);
    }
}
