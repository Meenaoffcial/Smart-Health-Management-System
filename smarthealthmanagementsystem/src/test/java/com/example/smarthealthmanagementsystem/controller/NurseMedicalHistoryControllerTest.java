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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class NurseMedicalHistoryControllerTest {

    @Mock
    private MedicalHistoryService medicalHistoryService;

    @InjectMocks
    private NurseMedicalHistoryController controller;

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
}