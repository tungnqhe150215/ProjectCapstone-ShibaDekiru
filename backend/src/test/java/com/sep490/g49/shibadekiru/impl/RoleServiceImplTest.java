package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.Role;
import com.sep490.g49.shibadekiru.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getByRoleId() {
        // Mocking the behavior of roleRepository.findRoleByRoleId() method
        Long roleId = 1L;
        Role expectedRole = new Role();
        when(roleRepository.findRoleByRoleId(roleId)).thenReturn(expectedRole);

        // Calling the method to be tested
        Role result = roleService.getByRoleId(roleId);

        // Assertions
        assertNotNull(result);
        assertEquals(expectedRole, result);
    }
}
