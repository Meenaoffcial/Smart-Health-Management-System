package com.example.smarthealthmanagementsystem.controller;

import com.example.smarthealthmanagementsystem.entity.ERole;
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

/** Controller for managing nurse user accounts by administrators. */
@RestController
@RequestMapping("/api/admin/nurses/users")
public class NurseController {

    private static final Logger logger = LoggerFactory.getLogger(NurseController.class);
    private final UserService userService;
    private final RoleService roleService;

    /** Constructor to inject UserService and RoleService. */
    @Autowired
    public NurseController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    /** Creates a new nurse user account. */
    @PostMapping
    public ResponseEntity<?> createNurse(@Valid @RequestBody User user) {
        logger.info("Attempting to create nurse: {}", user.getUsername());
        Role role = roleService.getRoleByName(ERole.NURSE).orElse(null);
        if (role == null) {
            logger.warn("Nurse role not found.");
            return new ResponseEntity<>("Error: Nurse role not found.", HttpStatus.NOT_FOUND);
        }
        user.setRole(role);
        User createdNurse = userService.createUser(user);
        logger.info("Nurse created successfully: {}", createdNurse.getUsername());
        return new ResponseEntity<>(createdNurse, HttpStatus.CREATED);
    }

    /** Retrieves a nurse user account by their ID. */
    @GetMapping("/{id}")
    public ResponseEntity<?> getNurseById(@PathVariable Integer id) {
        logger.info("Attempting to get nurse by ID: {}", id);
        Optional<User> nurseOptional = userService.getUserById(id);
        if (nurseOptional.isPresent()) {
            logger.info("Nurse found with ID: {}", id);
            return new ResponseEntity<>(nurseOptional.get(), HttpStatus.OK);
        } else {
            logger.warn("Nurse not found with ID: {}", id);
            return new ResponseEntity<>("Error: Nurse not found.", HttpStatus.NOT_FOUND);
        }
    }

    /** Retrieves all nurse user accounts. */
    @GetMapping
    public ResponseEntity<List<User>> getAllNurses() {
        logger.info("Attempting to get all nurses.");
        List<User> nurses = userService.getAllUsers();
        logger.info("Found {} nurses.", nurses.size());
        return new ResponseEntity<>(nurses, HttpStatus.OK);
    }

    /** Updates an existing nurse user account. */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateNurse(@PathVariable Integer id, @Valid @RequestBody User user) {
        logger.info("Attempting to update nurse with ID: {}", id);
        Role role = roleService.getRoleByName(ERole.NURSE).orElse(null);
        if (role == null) {
            logger.warn("Nurse role not found.");
            return new ResponseEntity<>("Error: Nurse role not found.", HttpStatus.NOT_FOUND);
        }
        user.setRole(role);
        user.setId(id);
        User updatedNurse = userService.updateUser(user);
        logger.info("Nurse updated successfully: {}", updatedNurse.getUsername());
        return new ResponseEntity<>(updatedNurse, HttpStatus.OK);
    }

    /** Deletes a nurse user account by their ID. */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNurse(@PathVariable Integer id) {
        logger.info("Attempting to delete nurse with ID: {}", id);
        userService.deleteUser(id);
        logger.info("Nurse deleted successfully with ID: {}", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}