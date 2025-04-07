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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Controller for user authentication and registration. */
@RestController
@RequestMapping("/api")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    /** Constructor to inject UserService, RoleService, and PasswordEncoder. */
    @Autowired
    public AuthController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    /** Registers a new user. */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User registrationRequest) {
        logger.info("Attempting to register user: {}", registrationRequest.getUsername());
        if (userService.isUsernameTaken(registrationRequest.getUsername())) {
            logger.warn("Registration failed: Username '{}' already exists.", registrationRequest.getUsername());
            return new ResponseEntity<>("Error: Username already exists.", HttpStatus.CONFLICT);
        }
        if (userService.isEmailTaken(registrationRequest.getEmail())) {
            logger.warn("Registration failed: Email '{}' already exists.", registrationRequest.getEmail());
            return new ResponseEntity<>("Error: Email already exists.", HttpStatus.CONFLICT);
        }

        ERole roleName;
        if (registrationRequest.getUsername().startsWith("admin")) {
            roleName = ERole.ADMIN;
        } else if (registrationRequest.getUsername().startsWith("doctor")) {
            roleName = ERole.DOCTOR;
        } else if (registrationRequest.getUsername().startsWith("nurse")) {
            roleName = ERole.NURSE;
        } else if (registrationRequest.getUsername().startsWith("patient")) {
            roleName = ERole.PATIENT;
        } else {
            logger.warn("Registration failed: Invalid username prefix for role determination.");
            return new ResponseEntity<>("Error: Invalid username prefix for role determination.", HttpStatus.BAD_REQUEST);
        }

        Role role = roleService.getRoleByName(roleName).orElse(null);
        if (role == null) {
            logger.error("Registration failed: Role not found in the database.");
            return new ResponseEntity<>("Error: Role not found.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        User newUser = new User();
        newUser.setUsername(registrationRequest.getUsername());
        newUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        newUser.setEmail(registrationRequest.getEmail());
        newUser.setRole(role);
        userService.createUser(newUser);
        logger.info("User '{}' registered successfully as {}.", newUser.getUsername(), roleName);
        return new ResponseEntity<>("patient  registered successfully.", HttpStatus.CREATED);
    }
}