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

public class DoctorControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private RoleService roleService;

    @InjectMocks
    private DoctorController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateDoctorSuccess() {
        User user = new User();
        user.setUsername("testDoctor");

        Role role = new Role();
        role.setId(1);
        role.setName(ERole.DOCTOR);

        User createdDoctor = new User();
        createdDoctor.setId(1);

        when(roleService.getRoleByName(ERole.DOCTOR)).thenReturn(Optional.of(role));
        when(userService.createUser(user)).thenReturn(createdDoctor);

        ResponseEntity<?> response = controller.createDoctor(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdDoctor, response.getBody());
        verify(userService, times(1)).createUser(user);
    }

    @Test
    public void testCreateDoctorRoleNotFound() {
        User user = new User();

        when(roleService.getRoleByName(ERole.DOCTOR)).thenReturn(Optional.empty());

        ResponseEntity<?> response = controller.createDoctor(user);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Error: Doctor role not found.", response.getBody());
        verify(userService, never()).createUser(user);
    }

    @Test
    public void testGetDoctorByIdFound() {
        Integer id = 1;
        User doctor = new User();
        doctor.setId(id);

        when(userService.getUserById(id)).thenReturn(Optional.of(doctor));

        ResponseEntity<?> response = controller.getDoctorById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(doctor, response.getBody());
    }

    @Test
    public void testGetDoctorByIdNotFound() {
        Integer id = 1;

        when(userService.getUserById(id)).thenReturn(Optional.empty());

        ResponseEntity<?> response = controller.getDoctorById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Error: Doctor not found.", response.getBody());
    }

    @Test
    public void testGetAllDoctors() {
        User doctor1 = new User();
        User doctor2 = new User();
        List<User> doctors = Arrays.asList(doctor1, doctor2);

        when(userService.getAllUsers()).thenReturn(doctors);

        ResponseEntity<List<User>> response = controller.getAllDoctors();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(doctors, response.getBody());
    }

    @Test
    public void testUpdateDoctorSuccess() {
        Integer id = 1;
        User user = new User();
        user.setId(id);
        user.setUsername("updatedDoctor");

        Role role = new Role();
        role.setId(1);
        role.setName(ERole.DOCTOR);

        User updatedDoctor = new User();
        updatedDoctor.setId(id);

        when(roleService.getRoleByName(ERole.DOCTOR)).thenReturn(Optional.of(role));
        when(userService.updateUser(user)).thenReturn(updatedDoctor);

        ResponseEntity<?> response = controller.updateDoctor(id, user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedDoctor, response.getBody());
        verify(userService, times(1)).updateUser(user);
    }

    @Test
    public void testUpdateDoctorRoleNotFound() {
        Integer id = 1;
        User user = new User();

        when(roleService.getRoleByName(ERole.DOCTOR)).thenReturn(Optional.empty());

        ResponseEntity<?> response = controller.updateDoctor(id, user);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Error: Doctor role not found.", response.getBody());
        verify(userService, never()).updateUser(user);
    }

    @Test
    public void testDeleteDoctor() {
        Integer id = 1;

        ResponseEntity<?> response = controller.deleteDoctor(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService, times(1)).deleteUser(id);
    }
}