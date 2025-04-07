package com.example.smarthealthmanagementsystem.service;

import com.example.smarthealthmanagementsystem.entity.PatientHealthData;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing patient health data (medical records).
 * Defines methods for creating, retrieving, and updating medical records.
 */
public interface PatientHealthDataService {

    /**
     * Creates a new medical record.
     *
     * @param record The PatientHealthData object to be created.
     * @return The created PatientHealthData object.
     */
    PatientHealthData createMedicalRecord(PatientHealthData record);

    /**
     * Retrieves a medical record by its ID.
     *
     * @param id The ID of the medical record.
     * @return An Optional containing the PatientHealthData object, or an empty Optional if not found.
     */
    Optional<PatientHealthData> getMedicalRecord(int id);

    /**
     * Retrieves a list of all medical records.
     *
     * @return A list of PatientHealthData objects.
     */
    List<PatientHealthData> getAllMedicalRecords();

    /**
     * Updates an existing medical record.
     *
     * @param id     The ID of the medical record to be updated.
     * @param record The PatientHealthData object with updated information.
     * @return The updated PatientHealthData object.
     */
    PatientHealthData updateMedicalRecord(int id, PatientHealthData record);
}