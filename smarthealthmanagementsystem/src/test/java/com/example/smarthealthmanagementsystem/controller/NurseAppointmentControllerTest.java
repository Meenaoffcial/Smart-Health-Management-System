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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class NurseAppointmentControllerTest {

    @Mock
    private AppointmentService appointmentService;

    @InjectMocks
    private NurseAppointmentController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddAppointment() {
        Appointment appointment = new Appointment();
        Appointment addedAppointment = new Appointment();
        addedAppointment.setId(1);

        when(appointmentService.bookAppointment(appointment)).thenReturn(addedAppointment);

        ResponseEntity<Appointment> response = controller.addAppointment(appointment);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(addedAppointment, response.getBody());
        verify(appointmentService, times(1)).bookAppointment(appointment);
    }

    @Test
    public void testUpdateAppointmentStatusFound() {
        Integer id = 1;
        String status = "Confirmed";

        Appointment existingAppointment = new Appointment();
        existingAppointment.setId(id);

        Appointment updatedAppointment = new Appointment();
        updatedAppointment.setId(id);
        updatedAppointment.setStatus(status);

        when(appointmentService.getAppointmentById(id)).thenReturn(Optional.of(existingAppointment));
        when(appointmentService.bookAppointment(any(Appointment.class))).thenReturn(updatedAppointment);

        ResponseEntity<Appointment> response = controller.updateAppointmentStatus(id, status);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedAppointment, response.getBody());
        verify(appointmentService, times(1)).getAppointmentById(id);
        verify(appointmentService, times(1)).bookAppointment(any(Appointment.class));
    }

    @Test
    public void testUpdateAppointmentStatusNotFound() {
        Integer id = 1;
        String status = "Confirmed";

        when(appointmentService.getAppointmentById(id)).thenReturn(Optional.empty());

        ResponseEntity<Appointment> response = controller.updateAppointmentStatus(id, status);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(appointmentService, times(1)).getAppointmentById(id);
        verify(appointmentService, never()).bookAppointment(any(Appointment.class));
    }
}