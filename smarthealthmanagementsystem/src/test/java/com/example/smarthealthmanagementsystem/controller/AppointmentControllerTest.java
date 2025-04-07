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

public class AppointmentControllerTest {

    @Mock
    private AppointmentService appointmentService;

    @InjectMocks
    private AppointmentController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBookAppointmentSuccess() {
        Appointment appointment = new Appointment();
        Appointment bookedAppointment = new Appointment();
        bookedAppointment.setId(1);

        when(appointmentService.bookAppointment(appointment)).thenReturn(bookedAppointment);

        ResponseEntity<String> response = controller.bookAppointment(appointment);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Appointment booked successfully with ID: 1. Confirmation email sent to patient.", response.getBody());
        verify(appointmentService, times(1)).bookAppointment(appointment);
    }

    @Test
    public void testBookAppointmentFailure() {
        Appointment appointment = new Appointment();

        when(appointmentService.bookAppointment(appointment)).thenThrow(new RuntimeException("Test Exception"));

        ResponseEntity<String> response = controller.bookAppointment(appointment);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Failed to book appointment: Test Exception", response.getBody());
        verify(appointmentService, times(1)).bookAppointment(appointment);
    }

    @Test
    public void testRescheduleAppointmentFound() {
        Integer id = 1;
        Appointment appointment = new Appointment();
        Appointment rescheduledAppointment = new Appointment();
        rescheduledAppointment.setId(id);

        when(appointmentService.rescheduleAppointment(id, appointment)).thenReturn(Optional.of(rescheduledAppointment));

        ResponseEntity<String> response = controller.rescheduleAppointment(id, appointment);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Appointment rescheduled successfully with ID: 1", response.getBody());
        verify(appointmentService, times(1)).rescheduleAppointment(id, appointment);
    }

    @Test
    public void testRescheduleAppointmentNotFound() {
        Integer id = 1;
        Appointment appointment = new Appointment();

        when(appointmentService.rescheduleAppointment(id, appointment)).thenReturn(Optional.empty());

        ResponseEntity<String> response = controller.rescheduleAppointment(id, appointment);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Appointment not found with ID: 1", response.getBody());
        verify(appointmentService, times(1)).rescheduleAppointment(id, appointment);
    }

    @Test
    public void testCancelAppointmentFound() {
        Integer id = 1;
        Appointment appointment = new Appointment();
        appointment.setId(id);

        when(appointmentService.getAppointmentById(id)).thenReturn(Optional.of(appointment));
        when(appointmentService.bookAppointment(any(Appointment.class))).thenReturn(appointment);

        ResponseEntity<String> response = controller.cancelAppointment(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Appointment cancelled successfully with ID: 1", response.getBody());
        verify(appointmentService, times(1)).getAppointmentById(id);
        verify(appointmentService, times(1)).bookAppointment(any(Appointment.class));
    }

    @Test
    public void testCancelAppointmentNotFound() {
        Integer id = 1;

        when(appointmentService.getAppointmentById(id)).thenReturn(Optional.empty());

        ResponseEntity<String> response = controller.cancelAppointment(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Appointment not found with ID: 1", response.getBody());
        verify(appointmentService, times(1)).getAppointmentById(id);
        verify(appointmentService, never()).bookAppointment(any(Appointment.class));
    }

    @Test
    public void testGetAppointmentByIdFound() {
        Integer id = 1;
        Appointment appointment = new Appointment();
        appointment.setId(id);

        when(appointmentService.getAppointmentById(id)).thenReturn(Optional.of(appointment));

        ResponseEntity<String> response = controller.getAppointmentById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Appointment found with ID: 1", response.getBody());
        verify(appointmentService, times(1)).getAppointmentById(id);
    }

    @Test
    public void testGetAppointmentByIdNotFound() {
        Integer id = 1;

        when(appointmentService.getAppointmentById(id)).thenReturn(Optional.empty());

        ResponseEntity<String> response = controller.getAppointmentById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Appointment not found with ID: 1", response.getBody());
        verify(appointmentService, times(1)).getAppointmentById(id);
    }

    @Test
    public void testGetAppointmentsByPatient() {
        Integer patientId = 1;
        List<Appointment> appointments = Arrays.asList(new Appointment(), new Appointment());

        when(appointmentService.getAppointmentsByPatientId(patientId)).thenReturn(appointments);

        ResponseEntity<String> response = controller.getAppointmentsByPatient(patientId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Appointments found for patient ID: 1. Count: 2", response.getBody());
        verify(appointmentService, times(1)).getAppointmentsByPatientId(patientId);
    }

    @Test
    public void testGetAppointmentsByDoctor() {
        Integer doctorId = 1;
        List<Appointment> appointments = Arrays.asList(new Appointment(), new Appointment());

        when(appointmentService.getAppointmentsByDoctorId(doctorId)).thenReturn(appointments);

        ResponseEntity<String> response = controller.getAppointmentsByDoctor(doctorId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Appointments found for doctor ID: 1. Count: 2", response.getBody());
        verify(appointmentService, times(1)).getAppointmentsByDoctorId(doctorId);
    }
}