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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class NurseControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private RoleService roleService;

    @InjectMocks
    private NurseController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateNurseSuccess() {
        User user = new User();
        user.setUsername("testNurse");

        Role role = new Role();
        role.setId(1);
        role.setName(ERole.NURSE);

        User createdNurse = new User();
        createdNurse.setId(1);

        when(roleService.getRoleByName(ERole.NURSE)).thenReturn(Optional.of(role));
        when(userService.createUser(user)).thenReturn(createdNurse);

        ResponseEntity<?> response = controller.createNurse(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdNurse, response.getBody());
        verify(userService, times(1)).createUser(user);
    }

    @Test
    public void testCreateNurseRoleNotFound() {
        User user = new User();

        when(roleService.getRoleByName(ERole.NURSE)).thenReturn(Optional.empty());

        ResponseEntity<?> response = controller.createNurse(user);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Error: Nurse role not found.", response.getBody());
        verify(userService, never()).createUser(user);
    }

    @Test
    public void testGetNurseByIdFound() {
        Integer id = 1;
        User nurse = new User();
        nurse.setId(id);

        when(userService.getUserById(id)).thenReturn(Optional.of(nurse));

        ResponseEntity<?> response = controller.getNurseById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(nurse, response.getBody());
    }

    @Test
    public void testGetNurseByIdNotFound() {
        Integer id = 1;

        when(userService.getUserById(id)).thenReturn(Optional.empty());

        ResponseEntity<?> response = controller.getNurseById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Error: Nurse not found.", response.getBody());
    }

    @Test
    public void testGetAllNurses() {
        User nurse1 = new User();
        User nurse2 = new User();
        List<User> nurses = Arrays.asList(nurse1, nurse2);

        when(userService.getAllUsers()).thenReturn(nurses);

        ResponseEntity<List<User>> response = controller.getAllNurses();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(nurses, response.getBody());
    }

    @Test
    public void testUpdateNurseSuccess() {
        Integer id = 1;
        User user = new User();
        user.setId(id);
        user.setUsername("updatedNurse");

        Role role = new Role();
        role.setId(1);
        role.setName(ERole.NURSE);

        User updatedNurse = new User();
        updatedNurse.setId(id);

        when(roleService.getRoleByName(ERole.NURSE)).thenReturn(Optional.of(role));
        when(userService.updateUser(user)).thenReturn(updatedNurse);

        ResponseEntity<?> response = controller.updateNurse(id, user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedNurse, response.getBody());
        verify(userService, times(1)).updateUser(user);
    }

    @Test
    public void testUpdateNurseRoleNotFound() {
        Integer id = 1;
        User user = new User();

        when(roleService.getRoleByName(ERole.NURSE)).thenReturn(Optional.empty());

        ResponseEntity<?> response = controller.updateNurse(id, user);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Error: Nurse role not found.", response.getBody());
        verify(userService, never()).updateUser(user);
    }

    @Test
    public void testDeleteNurse() {
        Integer id = 1;

        ResponseEntity<?> response = controller.deleteNurse(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService, times(1)).deleteUser(id);
    }
}