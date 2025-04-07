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

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AdminNursePatientMedicalHistoryControllerTest {

    @Mock
    private MedicalHistoryService medicalHistoryService;

    @InjectMocks
    private AdminNursePatientMedicalHistoryController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetMedicalHistoriesByPatient() {
        Integer patientId = 1;
        MedicalHistory history1 = new MedicalHistory();
        MedicalHistory history2 = new MedicalHistory();
        List<MedicalHistory> histories = Arrays.asList(history1, history2);

        when(medicalHistoryService.getMedicalHistoryByPatientId(patientId)).thenReturn(histories);

        ResponseEntity<List<MedicalHistory>> response = controller.getMedicalHistoriesByPatient(patientId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(histories, response.getBody());
        verify(medicalHistoryService, times(1)).getMedicalHistoryByPatientId(patientId);
    }

    @Test
    public void testAddMedicalHistory() {
        MedicalHistory medicalHistory = new MedicalHistory();
        MedicalHistory addedHistory = new MedicalHistory();
        addedHistory.setId(1);

        when(medicalHistoryService.addMedicalHistory(medicalHistory)).thenReturn(addedHistory);

        ResponseEntity<MedicalHistory> response = controller.addMedicalHistory(medicalHistory);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(addedHistory, response.getBody());
        verify(medicalHistoryService, times(1)).addMedicalHistory(medicalHistory);
    }

    @Test
    public void testUpdateMedicalHistory() {
        Integer id = 1;
        MedicalHistory medicalHistory = new MedicalHistory();
        MedicalHistory updatedHistory = new MedicalHistory();
        updatedHistory.setId(id);

        when(medicalHistoryService.updateMedicalHistory(medicalHistory)).thenReturn(updatedHistory);

        ResponseEntity<MedicalHistory> response = controller.updateMedicalHistory(id, medicalHistory);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedHistory, response.getBody());
        verify(medicalHistoryService, times(1)).updateMedicalHistory(medicalHistory);
    }

    @Test
    public void testDeleteMedicalHistory() {
        Integer id = 1;

        ResponseEntity<Void> response = controller.deleteMedicalHistory(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(medicalHistoryService, times(1)).deleteMedicalHistory(id);
    }
}