package com.example.smarthealthmanagementsystem.controller;

import com.example.smarthealthmanagementsystem.entity.Patient;
import com.example.smarthealthmanagementsystem.service.PatientService;
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

public class NursePatientControllerTest {

    @Mock
    private PatientService patientService;

    @InjectMocks
    private NursePatientController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetPatientByIdFound() {
        Integer id = 1;
        Patient patient = new Patient();
        patient.setId(id);
        patient.setFirstName("John");
        patient.setLastName("Doe");

        when(patientService.getPatientById(id)).thenReturn(Optional.of(patient));

        ResponseEntity<?> response = controller.getPatientById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(patient, response.getBody());
        verify(patientService, times(1)).getPatientById(id);
    }

    @Test
    public void testGetPatientByIdNotFound() {
        Integer id = 1;

        when(patientService.getPatientById(id)).thenReturn(Optional.empty());

        ResponseEntity<?> response = controller.getPatientById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Error: Patient not found with ID: " + id, response.getBody());
        verify(patientService, times(1)).getPatientById(id);
    }

    @Test
    public void testGetAllPatients() {
        Patient patient1 = new Patient();
        Patient patient2 = new Patient();
        List<Patient> patients = Arrays.asList(patient1, patient2);

        when(patientService.getAllPatients()).thenReturn(patients);

        ResponseEntity<List<Patient>> response = controller.getAllPatients();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(patients, response.getBody());
        verify(patientService, times(1)).getAllPatients();
    }
}