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

public class PatientControllerTest {

    @Mock
    private MedicalHistoryService medicalHistoryService;

    @InjectMocks
    private PatientController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetPatientMedicalHistory() {
        Integer patientId = 1;
        MedicalHistory history1 = new MedicalHistory();
        MedicalHistory history2 = new MedicalHistory();
        List<MedicalHistory> histories = Arrays.asList(history1, history2);

        when(medicalHistoryService.getMedicalHistoryByPatientId(patientId)).thenReturn(histories);

        ResponseEntity<List<MedicalHistory>> response = controller.getPatientMedicalHistory(patientId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(histories, response.getBody());
        verify(medicalHistoryService, times(1)).getMedicalHistoryByPatientId(patientId);
    }
}