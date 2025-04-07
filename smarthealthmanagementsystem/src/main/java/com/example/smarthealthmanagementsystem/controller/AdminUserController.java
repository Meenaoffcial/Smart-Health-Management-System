package com.example.smarthealthmanagementsystem.controller;

import com.example.smarthealthmanagementsystem.entity.Role;
import com.example.smarthealthmanagementsystem.entity.User;
import com.example.smarthealthmanagementsystem.service.RoleService;
import com.example.smarthealthmanagementsystem.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/** Controller for managing user accounts by administrators. */
@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {

    private final UserService userService;
    private final RoleService roleService;
    private static final Logger logger = LoggerFactory.getLogger(AdminUserController.class);

    /** Constructor to inject UserService and RoleService. */
    @Autowired
    public AdminUserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    /** Creates a new user account. */
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User newUser) {
        logger.info("Admin attempting to create user: {}", newUser.getUsername());
        if (userService.isUsernameTaken(newUser.getUsername())) {
            logger.warn("Username already exists: {}", newUser.getUsername());
            return new ResponseEntity<>(HttpStatus.CONFLICT); // Username already exists
        }
        if (userService.isEmailTaken(newUser.getEmail())) {
            logger.warn("Email already exists: {}", newUser.getEmail());
            return new ResponseEntity<>(HttpStatus.CONFLICT); // Email already exists
        }
        Optional<Role> roleOptional = roleService.getRoleById(newUser.getRole().getId());
        if (roleOptional.isEmpty()) {
            logger.warn("Invalid role ID: {}", newUser.getRole().getId());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Invalid role ID
        }
        newUser.setRole(roleOptional.get());
        User createdUser = userService.createUser(newUser);
        logger.info("User created successfully: {}", createdUser.getUsername());
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    /** Retrieves a user by their ID. */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        logger.info("Admin attempting to view user with ID: {}", id);
        Optional<User> user = userService.getUserById(id);
        return user.map(value -> {
            logger.info("User found with ID: {}", id);
            return new ResponseEntity<>(value, HttpStatus.OK);
        }).orElseGet(() -> {
            logger.warn("User not found with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        });
    }

    /** Retrieves all users. */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        logger.info("Admin attempting to view all users.");
        List<User> users = userService.getAllUsers();
        logger.info("Found {} users.", users.size());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /** Updates an existing user's information. */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @Valid @RequestBody User updatedUser) {
        logger.info("Admin attempting to update user with ID: {}", id);
        Optional<User> existingUserOptional = userService.getUserById(id);
        if (existingUserOptional.isEmpty()) {
            logger.warn("User not found with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User existingUser = existingUserOptional.get();

        if (!updatedUser.getUsername().equals(existingUser.getUsername()) && userService.isUsernameTaken(updatedUser.getUsername())) {
            logger.warn("New username already exists: {}", updatedUser.getUsername());
            return new ResponseEntity<>(HttpStatus.CONFLICT); // New username already exists
        }
        if (!updatedUser.getEmail().equals(existingUser.getEmail()) && userService.isEmailTaken(updatedUser.getEmail())) {
            logger.warn("New email already exists: {}", updatedUser.getEmail());
            return new ResponseEntity<>(HttpStatus.CONFLICT); // New email already exists
        }

        Optional<Role> roleOptional = roleService.getRoleById(updatedUser.getRole().getId());
        if (roleOptional.isEmpty()) {
            logger.warn("Invalid role ID: {}", updatedUser.getRole().getId());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Invalid role ID
        }

        updatedUser.setId(id); // Ensure the ID is set for updating
        updatedUser.setRole(roleOptional.get());
        User updated = userService.updateUser(updatedUser);
        if (updated != null) {
            logger.info("User updated successfully: {}", updated.getUsername());
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            logger.warn("Failed to update user with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Should not happen if we checked existence earlier
        }
    }

    /** Deletes a user by their ID. */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        logger.info("Admin attempting to delete user with ID: {}", id);
        if (userService.getUserById(id).isEmpty()) {
            logger.warn("User not found with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.deleteUser(id);
        logger.info("User deleted successfully with ID: {}", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}