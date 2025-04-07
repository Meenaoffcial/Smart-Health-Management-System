package com.example.smarthealthmanagementsystem.service.impl;

import com.example.smarthealthmanagementsystem.entity.PatientVisit;
import com.example.smarthealthmanagementsystem.entity.DoctorPerformance;
import com.example.smarthealthmanagementsystem.entity.MedicationStatistic;
import com.example.smarthealthmanagementsystem.repository.PatientVisitRepository;
import com.example.smarthealthmanagementsystem.repository.DoctorPerformanceRepository;
import com.example.smarthealthmanagementsystem.repository.MedicationStatisticRepository;
import com.example.smarthealthmanagementsystem.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Implementation of the AnalyticsService interface.
 * Provides methods to retrieve analytics data from repositories.
 */
@Service
public class AnalyticsServiceImpl implements AnalyticsService {

    private final PatientVisitRepository patientVisitRepository;
    private final DoctorPerformanceRepository doctorPerformanceRepository;
    private final MedicationStatisticRepository medicationStatisticRepository;

    /**
     * Constructor to inject repositories.
     *
     * @param patientVisitRepository      Repository for PatientVisit entities.
     * @param doctorPerformanceRepository Repository for DoctorPerformance entities.
     * @param medicationStatisticRepository Repository for MedicationStatistic entities.
     */
    @Autowired
    public AnalyticsServiceImpl(PatientVisitRepository patientVisitRepository,
                                DoctorPerformanceRepository doctorPerformanceRepository,
                                MedicationStatisticRepository medicationStatisticRepository) {
        this.patientVisitRepository = patientVisitRepository;
        this.doctorPerformanceRepository = doctorPerformanceRepository;
        this.medicationStatisticRepository = medicationStatisticRepository;
    }

    /**
     * Retrieves a list of all patient visits.
     *
     * @return A list of PatientVisit objects.
     */
    @Override
    public List<PatientVisit> getAllPatientVisits() {
        return patientVisitRepository.findAll();
    }

    /**
     * Retrieves a list of all doctor performance data.
     *
     * @return A list of DoctorPerformance objects.
     */
    @Override
    public List<DoctorPerformance> getAllDoctorPerformance() {
        return doctorPerformanceRepository.findAll();
    }

    /**
     * Retrieves a list of all medication statistics.
     *
     * @return A list of MedicationStatistic objects.
     */
    @Override
    public List<MedicationStatistic> getAllMedicationStatistics() {
        return medicationStatisticRepository.findAll();
    }
}