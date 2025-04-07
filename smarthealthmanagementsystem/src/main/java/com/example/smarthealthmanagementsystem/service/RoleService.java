package com.example.smarthealthmanagementsystem.service;

import com.example.smarthealthmanagementsystem.entity.ERole;
import com.example.smarthealthmanagementsystem.entity.Role;
import java.util.Optional;

/**
 * Service interface for managing user roles.
 * Defines methods for retrieving roles by ID or name.
 */
public interface RoleService {

    /**
     * Retrieves a role by its ID.
     *
     * @param id The ID of the role.
     * @return An Optional containing the Role object, or an empty Optional if not found.
     */
    Optional<Role> getRoleById(Integer id);

    /**
     * Retrieves a role by its name (ERole enum).
     *
     * @param name The name of the role (ERole enum).
     * @return An Optional containing the Role object, or an empty Optional if not found.
     */
    Optional<Role> getRoleByName(ERole name);
}