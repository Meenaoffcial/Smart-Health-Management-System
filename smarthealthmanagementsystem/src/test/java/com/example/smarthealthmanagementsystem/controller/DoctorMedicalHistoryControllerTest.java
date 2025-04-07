package com.example.smarthealthmanagementsystem.controller;

import com.example.smarthealthmanagementsystem.entity.MedicalHistory;
import com.example.smarthealthmanagementsystem.service.MedicalHistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class DoctorMedicalHistoryControllerTest {

    @Mock
    private MedicalHistoryService medicalHistoryService;

    @InjectMocks
    private DoctorMedicalHistoryController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddMedicalHistory() {
        MedicalHistory medicalHistory = new MedicalHistory();
        MedicalHistory addedHistory = new MedicalHistory();
        addedHistory.setId(1);

        when(medicalHistoryService.addMedicalHistory(medicalHistory)).thenReturn(addedHistory);

        ResponseEntity<Object> response = controller.addMedicalHistory(medicalHistory);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Medical history added successfully: 1", response.getBody());
        verify(medicalHistoryService, times(1)).addMedicalHistory(medicalHistory);
    }

    @Test
    public void testGetMedicalHistoryByIdFound() {
        Integer id = 1;
        MedicalHistory history = new MedicalHistory();
        history.setId(id);

        when(medicalHistoryService.getMedicalHistoryById(id)).thenReturn(Optional.of(history));

        ResponseEntity<Object> response = controller.getMedicalHistoryById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(history, response.getBody());
        verify(medicalHistoryService, times(1)).getMedicalHistoryById(id);
    }

    @Test
    public void testGetMedicalHistoryByIdNotFound() {
        Integer id = 1;

        when(medicalHistoryService.getMedicalHistoryById(id)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = controller.getMedicalHistoryById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Medical history not found", response.getBody());
        verify(medicalHistoryService, times(1)).getMedicalHistoryById(id);
    }

    @Test
    public void testUpdateMedicalHistoryFound() {
        Integer id = 1;
        MedicalHistory medicalHistory = new MedicalHistory();
        MedicalHistory updatedHistory = new MedicalHistory();
        updatedHistory.setId(id);

        when(medicalHistoryService.getMedicalHistoryById(id)).thenReturn(Optional.of(medicalHistory));
        when(medicalHistoryService.updateMedicalHistory(medicalHistory)).thenReturn(updatedHistory);

        ResponseEntity<Object> response = controller.updateMedicalHistory(id, medicalHistory);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Medical history updated successfully", response.getBody());
        verify(medicalHistoryService, times(1)).updateMedicalHistory(medicalHistory);
    }

    @Test
    public void testUpdateMedicalHistoryNotFound() {
        Integer id = 1;
        MedicalHistory medicalHistory = new MedicalHistory();

        when(medicalHistoryService.getMedicalHistoryById(id)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = controller.updateMedicalHistory(id, medicalHistory);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Medical history not found", response.getBody());
        verify(medicalHistoryService, never()).updateMedicalHistory(medicalHistory);
    }

    @Test
    public void testUpdateMedicalHistoryException() {
        Integer id = 1;
        MedicalHistory medicalHistory = new MedicalHistory();

        when(medicalHistoryService.getMedicalHistoryById(id)).thenReturn(Optional.of(medicalHistory));
        when(medicalHistoryService.updateMedicalHistory(medicalHistory)).thenThrow(new RuntimeException("Test Exception"));

        assertThrows(ResponseStatusException.class, () -> controller.updateMedicalHistory(id, medicalHistory));
    }

    @Test
    public void testDeleteMedicalHistory() {
        Integer id = 1;

        ResponseEntity<Object> response = controller.deleteMedicalHistory(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("Medical history deleted successfully", response.getBody());
        verify(medicalHistoryService, times(1)).deleteMedicalHistory(id);
    }

    @Test
    public void testGetAllMedicalHistoryByPatientId() {
        Integer patientId = 1;
        MedicalHistory history1 = new MedicalHistory();
        MedicalHistory history2 = new MedicalHistory();
        List<MedicalHistory> histories = Arrays.asList(history1, history2);

        when(medicalHistoryService.getMedicalHistoryByPatientId(patientId)).thenReturn(histories);

        ResponseEntity<List<MedicalHistory>> response = controller.getAllMedicalHistoryByPatientId(patientId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(histories, response.getBody());
        verify(medicalHistoryService, times(1)).getMedicalHistoryByPatientId(patientId);
    }

    @Test
    public void testGetAllMedicalHistories() {
        MedicalHistory history1 = new MedicalHistory();
        MedicalHistory history2 = new MedicalHistory();
        List<MedicalHistory> histories = Arrays.asList(history1, history2);

        when(medicalHistoryService.getAllMedicalHistories()).thenReturn(histories);

        ResponseEntity<List<MedicalHistory>> response = controller.getAllMedicalHistories();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(histories, response.getBody());
        verify(medicalHistoryService, times(1)).getAllMedicalHistories();
    }
}