package com.example.smarthealthmanagementsystem.controller;

import com.example.smarthealthmanagementsystem.entity.PatientHealthData;
import com.example.smarthealthmanagementsystem.service.PatientHealthDataService;
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

public class PatientHealthDataControllerTest {

    @Mock
    private PatientHealthDataService patientHealthDataService;

    @InjectMocks
    private PatientHealthDataController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateMedicalRecord() {
        PatientHealthData record = new PatientHealthData();
        PatientHealthData createdRecord = new PatientHealthData();
        createdRecord.setId(1);

        when(patientHealthDataService.createMedicalRecord(record)).thenReturn(createdRecord);

        ResponseEntity<String> response = controller.createMedicalRecord(record);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Medical record created with ID: 1", response.getBody());
        verify(patientHealthDataService, times(1)).createMedicalRecord(record);
    }

    @Test
    public void testGetMedicalRecordFound() {
        int id = 1;
        PatientHealthData record = new PatientHealthData();
        record.setId(id);

        when(patientHealthDataService.getMedicalRecord(id)).thenReturn(Optional.of(record));

        ResponseEntity<?> response = controller.getMedicalRecord(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(record, response.getBody());
        verify(patientHealthDataService, times(1)).getMedicalRecord(id);
    }

    @Test
    public void testGetMedicalRecordNotFound() {
        int id = 1;

        when(patientHealthDataService.getMedicalRecord(id)).thenReturn(Optional.empty());

        ResponseEntity<?> response = controller.getMedicalRecord(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Medical record not found", response.getBody());
        verify(patientHealthDataService, times(1)).getMedicalRecord(id);
    }

    @Test
    public void testGetAllMedicalRecords() {
        PatientHealthData record1 = new PatientHealthData();
        PatientHealthData record2 = new PatientHealthData();
        List<PatientHealthData> records = Arrays.asList(record1, record2);

        when(patientHealthDataService.getAllMedicalRecords()).thenReturn(records);

        ResponseEntity<List<PatientHealthData>> response = controller.getAllMedicalRecords();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(records, response.getBody());
        verify(patientHealthDataService, times(1)).getAllMedicalRecords();
    }

    @Test
    public void testUpdateMedicalRecord() {
        int id = 1;
        PatientHealthData record = new PatientHealthData();
        PatientHealthData updatedRecord = new PatientHealthData();
        updatedRecord.setId(id);

        when(patientHealthDataService.updateMedicalRecord(id, record)).thenReturn(updatedRecord);

        ResponseEntity<String> response = controller.updateMedicalRecord(id, record);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Medical record updated with ID: 1", response.getBody());
        verify(patientHealthDataService, times(1)).updateMedicalRecord(id, record);
    }
}