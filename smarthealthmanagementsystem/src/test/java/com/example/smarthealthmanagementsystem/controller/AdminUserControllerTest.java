package com.example.smarthealthmanagementsystem.controller;

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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AdminUserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private RoleService roleService;

    @InjectMocks
    private AdminUserController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUserSuccess() {
        User newUser = new User();
        newUser.setUsername("testUser");
        newUser.setEmail("test@example.com");
        Role role = new Role();
        role.setId(1);
        newUser.setRole(role);

        Role existingRole = new Role();
        existingRole.setId(1);

        User createdUser = new User();
        createdUser.setId(1);

        when(userService.isUsernameTaken(newUser.getUsername())).thenReturn(false);
        when(userService.isEmailTaken(newUser.getEmail())).thenReturn(false);
        when(roleService.getRoleById(newUser.getRole().getId())).thenReturn(Optional.of(existingRole));
        when(userService.createUser(newUser)).thenReturn(createdUser);

        ResponseEntity<User> response = controller.createUser(newUser);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdUser, response.getBody());
        verify(userService, times(1)).createUser(newUser);
    }

    @Test
    public void testCreateUserUsernameTaken() {
        User newUser = new User();
        newUser.setUsername("testUser");

        when(userService.isUsernameTaken(newUser.getUsername())).thenReturn(true);

        ResponseEntity<User> response = controller.createUser(newUser);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        verify(userService, never()).createUser(newUser);
    }

    @Test
    public void testCreateUserEmailTaken() {
        User newUser = new User();
        newUser.setUsername("testUser");
        newUser.setEmail("test@example.com");

        when(userService.isUsernameTaken(newUser.getUsername())).thenReturn(false);
        when(userService.isEmailTaken(newUser.getEmail())).thenReturn(true);

        ResponseEntity<User> response = controller.createUser(newUser);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        verify(userService, never()).createUser(newUser);
    }

    @Test
    public void testCreateUserInvalidRoleId() {
        User newUser = new User();
        newUser.setUsername("testUser");
        newUser.setEmail("test@example.com");
        Role role = new Role();
        role.setId(1);
        newUser.setRole(role);

        when(userService.isUsernameTaken(newUser.getUsername())).thenReturn(false);
        when(userService.isEmailTaken(newUser.getEmail())).thenReturn(false);
        when(roleService.getRoleById(newUser.getRole().getId())).thenReturn(Optional.empty());

        ResponseEntity<User> response = controller.createUser(newUser);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(userService, never()).createUser(newUser);
    }

    @Test
    public void testGetUserByIdFound() {
        Integer id = 1;
        User user = new User();
        user.setId(id);

        when(userService.getUserById(id)).thenReturn(Optional.of(user));

        ResponseEntity<User> response = controller.getUserById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testGetUserByIdNotFound() {
        Integer id = 1;

        when(userService.getUserById(id)).thenReturn(Optional.empty());

        ResponseEntity<User> response = controller.getUserById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetAllUsers() {
        User user1 = new User();
        User user2 = new User();
        List<User> users = Arrays.asList(user1, user2);

        when(userService.getAllUsers()).thenReturn(users);

        ResponseEntity<List<User>> response = controller.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
    }

    @Test
    public void testUpdateUserSuccess() {
        Integer id = 1;
        User updatedUser = new User();
        updatedUser.setId(id);
        updatedUser.setUsername("updatedUser");
        updatedUser.setEmail("updated@example.com");
        Role role = new Role();
        role.setId(2);
        updatedUser.setRole(role);

        User existingUser = new User();
        existingUser.setId(id);
        existingUser.setUsername("testUser");
        existingUser.setEmail("test@example.com");
        Role existingRole = new Role();
        existingRole.setId(1);
        existingUser.setRole(existingRole);

        Role updatedRole = new Role();
        updatedRole.setId(2);

        when(userService.getUserById(id)).thenReturn(Optional.of(existingUser));
        when(userService.isUsernameTaken(updatedUser.getUsername())).thenReturn(false);
        when(userService.isEmailTaken(updatedUser.getEmail())).thenReturn(false);
        when(roleService.getRoleById(updatedUser.getRole().getId())).thenReturn(Optional.of(updatedRole));
        when(userService.updateUser(updatedUser)).thenReturn(updatedUser);

        ResponseEntity<User> response = controller.updateUser(id, updatedUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedUser, response.getBody());
    }

    @Test
    public void testUpdateUserNotFound() {
        Integer id = 1;
        User updatedUser = new User();

        when(userService.getUserById(id)).thenReturn(Optional.empty());

        ResponseEntity<User> response = controller.updateUser(id, updatedUser);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    

    @Test
    public void testDeleteUserFound() {
        Integer id = 1;
        User user = new User();
        user.setId(id);

        when(userService.getUserById(id)).thenReturn(Optional.of(user));

        ResponseEntity<Void> response = controller.deleteUser(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService, times(1)).deleteUser(id);
    }

    @Test
    public void testDeleteUserNotFound() {
        Integer id = 1;

        when(userService.getUserById(id)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = controller.deleteUser(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, never()).deleteUser(id);
    }
}