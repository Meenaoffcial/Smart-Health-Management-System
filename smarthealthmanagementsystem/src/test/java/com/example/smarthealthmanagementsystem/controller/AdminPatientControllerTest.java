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

public class AdminPatientControllerTest {

    @Mock
    private PatientService patientService;

    @InjectMocks
    private AdminPatientController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreatePatient() {
        Patient newPatient = new Patient();
        newPatient.setFirstName("John");
        newPatient.setLastName("Doe");
        Patient createdPatient = new Patient();
        createdPatient.setId(1);

        when(patientService.createPatient(newPatient)).thenReturn(createdPatient);

        ResponseEntity<?> response = controller.createPatient(newPatient);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Patient added by admin successfully", response.getBody());
        verify(patientService, times(1)).createPatient(newPatient);
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

    @Test
    public void testUpdatePatientFound() {
        Integer id = 1;
        Patient updatedPatient = new Patient();
        updatedPatient.setId(id);
        updatedPatient.setFirstName("Jane");
        updatedPatient.setLastName("Doe");

        Patient existingPatient = new Patient();
        existingPatient.setId(id);
        existingPatient.setFirstName("John");
        existingPatient.setLastName("Doe");

        when(patientService.getPatientById(id)).thenReturn(Optional.of(existingPatient));
        when(patientService.updatePatient(updatedPatient)).thenReturn(updatedPatient);

        ResponseEntity<?> response = controller.updatePatient(id, updatedPatient);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Patient updated by admin successfully", response.getBody());
        verify(patientService, times(1)).getPatientById(id);
        verify(patientService, times(1)).updatePatient(updatedPatient);
    }

    @Test
    public void testUpdatePatientNotFound() {
        Integer id = 1;
        Patient updatedPatient = new Patient();

        when(patientService.getPatientById(id)).thenReturn(Optional.empty());

        ResponseEntity<?> response = controller.updatePatient(id, updatedPatient);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Error: Patient not found with ID: " + id, response.getBody());
        verify(patientService, times(1)).getPatientById(id);
        verify(patientService, never()).updatePatient(updatedPatient);
    }

    @Test
    public void testDeletePatientFound() {
        Integer id = 1;
        Patient patient = new Patient();
        patient.setId(id);
        patient.setFirstName("John");
        patient.setLastName("Doe");

        when(patientService.getPatientById(id)).thenReturn(Optional.of(patient));

        ResponseEntity<?> response = controller.deletePatient(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("Patient deleted by admin successfully", response.getBody());
        verify(patientService, times(1)).getPatientById(id);
        verify(patientService, times(1)).deletePatient(id);
    }

    @Test
    public void testDeletePatientNotFound() {
        Integer id = 1;

        when(patientService.getPatientById(id)).thenReturn(Optional.empty());

        ResponseEntity<?> response = controller.deletePatient(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Error: Patient not found with ID: " + id, response.getBody());
        verify(patientService, times(1)).getPatientById(id);
        verify(patientService, never()).deletePatient(id);
    }
}