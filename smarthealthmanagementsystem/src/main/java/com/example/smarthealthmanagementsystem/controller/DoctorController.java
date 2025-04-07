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

/** Controller for managing doctor user accounts by administrators. */
@RestController
@RequestMapping("/api/admin/doctors/users")
public class DoctorController {

    private static final Logger logger = LoggerFactory.getLogger(DoctorController.class);
    private final UserService userService;
    private final RoleService roleService;

    /** Constructor to inject UserService and RoleService. */
    @Autowired
    public DoctorController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    /** Creates a new doctor user account. */
    @PostMapping
    public ResponseEntity<?> createDoctor(@Valid @RequestBody User user) {
        logger.info("Attempting to create doctor: {}", user.getUsername());
        Role role = roleService.getRoleByName(ERole.DOCTOR).orElse(null);
        if (role == null) {
            logger.warn("Doctor role not found.");
            return new ResponseEntity<>("Error: Doctor role not found.", HttpStatus.NOT_FOUND);
        }
        user.setRole(role);
        User createdDoctor = userService.createUser(user);
        logger.info("Doctor created successfully: {}", createdDoctor.getUsername());
        return new ResponseEntity<>(createdDoctor, HttpStatus.CREATED);
    }

    /** Retrieves a doctor user account by their ID. */
    @GetMapping("/{id}")
    public ResponseEntity<?> getDoctorById(@PathVariable Integer id) {
        logger.info("Attempting to get doctor by ID: {}", id);
        Optional<User> doctorOptional = userService.getUserById(id);
        if (doctorOptional.isPresent()) {
            logger.info("Doctor found with ID: {}", id);
            return new ResponseEntity<>(doctorOptional.get(), HttpStatus.OK);
        } else {
            logger.warn("Doctor not found with ID: {}", id);
            return new ResponseEntity<>("Error: Doctor not found.", HttpStatus.NOT_FOUND);
        }
    }

    /** Retrieves all doctor user accounts. */
    @GetMapping
    public ResponseEntity<List<User>> getAllDoctors() {
        logger.info("Attempting to get all doctors.");
        List<User> doctors = userService.getAllUsers();
        logger.info("Found {} doctors.", doctors.size());
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    /** Updates an existing doctor user account. */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDoctor(@PathVariable Integer id, @Valid @RequestBody User user) {
        logger.info("Attempting to update doctor with ID: {}", id);
        Role role = roleService.getRoleByName(ERole.DOCTOR).orElse(null);
        if (role == null) {
            logger.warn("Doctor role not found.");
            return new ResponseEntity<>("Error: Doctor role not found.", HttpStatus.NOT_FOUND);
        }
        user.setRole(role);
        user.setId(id);
        User updatedDoctor = userService.updateUser(user);
        logger.info("Doctor updated successfully: {}", updatedDoctor.getUsername());
        return new ResponseEntity<>(updatedDoctor, HttpStatus.OK);
    }

    /** Deletes a doctor user account by their ID. */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDoctor(@PathVariable Integer id) {
        logger.info("Attempting to delete doctor with ID: {}", id);
        userService.deleteUser(id);
        logger.info("Doctor deleted successfully with ID: {}", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}