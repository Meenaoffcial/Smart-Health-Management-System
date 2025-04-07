package com.example.smarthealthmanagementsystem.service;

import com.example.smarthealthmanagementsystem.entity.PatientVisit;
import com.example.smarthealthmanagementsystem.entity.DoctorPerformance;
import com.example.smarthealthmanagementsystem.entity.MedicationStatistic;
import java.util.List;

/**
 * Service interface for providing analytics data.
 * Defines methods to retrieve various analytics information.
 */
public interface AnalyticsService {

    /**
     * Retrieves a list of all patient visits.
     *
     * @return A list of PatientVisit objects.
     */
    List<PatientVisit> getAllPatientVisits();

    /**
     * Retrieves a list of all doctor performance data.
     *
     * @return A list of DoctorPerformance objects.
     */
    List<DoctorPerformance> getAllDoctorPerformance();

    /**
     * Retrieves a list of all medication statistics.
     *
     * @return A list of MedicationStatistic objects.
     */
    List<MedicationStatistic> getAllMedicationStatistics();
}