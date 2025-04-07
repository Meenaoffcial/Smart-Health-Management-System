package com.example.smarthealthmanagementsystem.controller;

import com.example.smarthealthmanagementsystem.entity.PatientVisit;
import com.example.smarthealthmanagementsystem.entity.DoctorPerformance;
import com.example.smarthealthmanagementsystem.entity.MedicationStatistic;
import com.example.smarthealthmanagementsystem.service.AnalyticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/** Controller for providing analytics data. */
@RestController
@RequestMapping("/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;
    private static final Logger logger = LoggerFactory.getLogger(AnalyticsController.class);

    /** Constructor to inject AnalyticsService. */
    @Autowired
    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    /** Retrieves all patient visit statistics. */
    @GetMapping("/patient-visits")
    public List<PatientVisit> getPatientVisits() {
        logger.info("Retrieving patient visit statistics.");
        List<PatientVisit> patientVisits = analyticsService.getAllPatientVisits();
        logger.info("Retrieved {} patient visit entries.", patientVisits.size());
        return patientVisits;
    }

    /** Retrieves all doctor performance statistics. */
    @GetMapping("/doctor-performance")
    public List<DoctorPerformance> getDoctorPerformance() {
        logger.info("Retrieving doctor performance statistics.");
        List<DoctorPerformance> doctorPerformances = analyticsService.getAllDoctorPerformance();
        logger.info("Retrieved {} doctor performance entries.", doctorPerformances.size());
        return doctorPerformances;
    }

    /** Retrieves all medication statistics. */
    @GetMapping("/medication-statistics")
    public List<MedicationStatistic> getMedicationStatistics() {
        logger.info("Retrieving medication statistics.");
        List<MedicationStatistic> medicationStatistics = analyticsService.getAllMedicationStatistics();
        logger.info("Retrieved {} medication statistic entries.", medicationStatistics.size());
        return medicationStatistics;
    }
}