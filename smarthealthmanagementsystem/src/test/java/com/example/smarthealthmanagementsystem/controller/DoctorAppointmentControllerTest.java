package com.example.smarthealthmanagementsystem.controller;

import com.example.smarthealthmanagementsystem.entity.Appointment;
import com.example.smarthealthmanagementsystem.service.AppointmentService;
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

public class DoctorAppointmentControllerTest {

    @Mock
    private AppointmentService appointmentService;

    @InjectMocks
    private DoctorAppointmentController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAppointmentByIdFound() {
        Integer id = 1;
        Appointment appointment = new Appointment();
        appointment.setId(id);

        when(appointmentService.getAppointmentById(id)).thenReturn(Optional.of(appointment));

        ResponseEntity<Appointment> response = controller.getAppointmentById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(appointment, response.getBody());
    }

    @Test
    public void testGetAppointmentByIdNotFound() {
        Integer id = 1;

        when(appointmentService.getAppointmentById(id)).thenReturn(Optional.empty());

        ResponseEntity<Appointment> response = controller.getAppointmentById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetAppointmentsByPatientId() {
        Integer patientId = 1;
        Appointment appointment1 = new Appointment();
        Appointment appointment2 = new Appointment();
        List<Appointment> appointments = Arrays.asList(appointment1, appointment2);

        when(appointmentService.getAppointmentsByPatientId(patientId)).thenReturn(appointments);

        ResponseEntity<List<Appointment>> response = controller.getAppointmentsByPatientId(patientId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(appointments, response.getBody());
    }

    @Test
    public void testGetAppointmentsByDoctorId() {
        Integer doctorId = 1;
        Appointment appointment1 = new Appointment();
        Appointment appointment2 = new Appointment();
        List<Appointment> appointments = Arrays.asList(appointment1, appointment2);

        when(appointmentService.getAppointmentsByDoctorId(doctorId)).thenReturn(appointments);

        ResponseEntity<List<Appointment>> response = controller.getAppointmentsByDoctorId(doctorId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(appointments, response.getBody());
    }
}