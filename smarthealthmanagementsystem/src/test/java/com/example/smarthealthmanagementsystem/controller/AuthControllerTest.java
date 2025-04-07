package com.example.smarthealthmanagementsystem.controller;

import com.example.smarthealthmanagementsystem.entity.ERole;
import com.example.smarthealthmanagementsystem.entity.Role;
import com.example.smarthealthmanagementsystem.entity.User;
import com.example.smarthealthmanagementsystem.service.RoleService;
import com.example.smarthealthmanagementsystem.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private RoleService roleService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

   

    @Test
    public void testRegisterUserUsernameTaken() {
        User registrationRequest = new User();
        registrationRequest.setUsername("patient123");

        when(userService.isUsernameTaken(registrationRequest.getUsername())).thenReturn(true);

        ResponseEntity<?> response = controller.registerUser(registrationRequest);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Error: Username already exists.", response.getBody());
        verify(userService, never()).createUser(any(User.class));
    }

    @Test
    public void testRegisterUserEmailTaken() {
        User registrationRequest = new User();
        registrationRequest.setUsername("patient123");
        registrationRequest.setEmail("patient@example.com");

        when(userService.isUsernameTaken(registrationRequest.getUsername())).thenReturn(false);
        when(userService.isEmailTaken(registrationRequest.getEmail())).thenReturn(true);

        ResponseEntity<?> response = controller.registerUser(registrationRequest);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Error: Email already exists.", response.getBody());
        verify(userService, never()).createUser(any(User.class));
    }

    @Test
    public void testRegisterUserRoleNotFound() {
        User registrationRequest = new User();
        registrationRequest.setUsername("patient123");
        registrationRequest.setEmail("patient@example.com");

        when(userService.isUsernameTaken(registrationRequest.getUsername())).thenReturn(false);
        when(userService.isEmailTaken(registrationRequest.getEmail())).thenReturn(false);
        when(roleService.getRoleByName(ERole.PATIENT)).thenReturn(Optional.empty());

        ResponseEntity<?> response = controller.registerUser(registrationRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error: Role not found.", response.getBody());
        verify(userService, never()).createUser(any(User.class));
    }

    @Test
    public void testRegisterUserInvalidPrefix() {
        User registrationRequest = new User();
        registrationRequest.setUsername("invalidUser");

        ResponseEntity<?> response = controller.registerUser(registrationRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error: Invalid username prefix for role determination.", response.getBody());
        verify(userService, never()).createUser(any(User.class));
    }
}