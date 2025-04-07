package com.example.smarthealthmanagementsystem.service.impl;

import com.example.smarthealthmanagementsystem.entity.ERole;
import com.example.smarthealthmanagementsystem.entity.Role;
import com.example.smarthealthmanagementsystem.repository.RoleRepository;
import com.example.smarthealthmanagementsystem.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of the RoleService interface.
 * Provides methods for retrieving user roles.
 */
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    /**
     * Constructor to inject RoleRepository.
     *
     * @param roleRepository Repository for Role entities.
     */
    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Retrieves a role by its ID.
     *
     * @param id The ID of the role.
     * @return An Optional containing the Role object, or an empty Optional if not found.
     */
    @Override
    public Optional<Role> getRoleById(Integer id) {
        return roleRepository.findById(id);
    }

    /**
     * Retrieves a role by its name (ERole enum).
     *
     * @param name The name of the role (ERole enum).
     * @return An Optional containing the Role object, or an empty Optional if not found.
     */
    @Override
    public Optional<Role> getRoleByName(ERole name) {
        return roleRepository.findByName(name);
    }
}