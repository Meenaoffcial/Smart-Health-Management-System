package com.example.smarthealthmanagementsystem.controller;

import com.example.smarthealthmanagementsystem.entity.PatientVisit;
import com.example.smarthealthmanagementsystem.entity.DoctorPerformance;
import com.example.smarthealthmanagementsystem.entity.MedicationStatistic;
import com.example.smarthealthmanagementsystem.service.AnalyticsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AnalyticsControllerTest {

    @Mock
    private AnalyticsService analyticsService;

    @InjectMocks
    private AnalyticsController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetPatientVisits() {
        PatientVisit visit1 = new PatientVisit();
        PatientVisit visit2 = new PatientVisit();
        List<PatientVisit> visits = Arrays.asList(visit1, visit2);

        when(analyticsService.getAllPatientVisits()).thenReturn(visits);

        List<PatientVisit> result = controller.getPatientVisits();

        assertEquals(visits, result);
        verify(analyticsService, times(1)).getAllPatientVisits();
    }

    @Test
    public void testGetDoctorPerformance() {
        DoctorPerformance performance1 = new DoctorPerformance();
        DoctorPerformance performance2 = new DoctorPerformance();
        List<DoctorPerformance> performances = Arrays.asList(performance1, performance2);

        when(analyticsService.getAllDoctorPerformance()).thenReturn(performances);

        List<DoctorPerformance> result = controller.getDoctorPerformance();

        assertEquals(performances, result);
        verify(analyticsService, times(1)).getAllDoctorPerformance();
    }

    @Test
    public void testGetMedicationStatistics() {
        MedicationStatistic statistic1 = new MedicationStatistic();
        MedicationStatistic statistic2 = new MedicationStatistic();
        List<MedicationStatistic> statistics = Arrays.asList(statistic1, statistic2);

        when(analyticsService.getAllMedicationStatistics()).thenReturn(statistics);

        List<MedicationStatistic> result = controller.getMedicationStatistics();

        assertEquals(statistics, result);
        verify(analyticsService, times(1)).getAllMedicationStatistics();
    }
}