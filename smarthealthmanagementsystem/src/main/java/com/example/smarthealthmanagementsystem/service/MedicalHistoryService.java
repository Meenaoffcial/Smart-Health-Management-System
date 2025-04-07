package com.example.smarthealthmanagementsystem.service;

import com.example.smarthealthmanagementsystem.entity.MedicalHistory;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing patient medical history.
 * Defines methods for retrieving, adding, updating, and deleting medical history records.
 */
public interface MedicalHistoryService {

    /**
     * Retrieves a list of medical history records for a specific patient.
     *
     * @param patientId The ID of the patient.
     * @return A list of MedicalHistory objects associated with the patient.
     */
    List<MedicalHistory> getMedicalHistoryByPatientId(Integer patientId);

    /**
     * Adds a new medical history record.
     *
     * @param medicalHistory The MedicalHistory object to be added.
     * @return The added MedicalHistory object.
     */
    MedicalHistory addMedicalHistory(MedicalHistory medicalHistory);

    /**
     * Retrieves a medical history record by its ID.
     *
     * @param id The ID of the medical history record.
     * @return An Optional containing the MedicalHistory object, or an empty Optional if not found.
     */
    Optional<MedicalHistory> getMedicalHistoryById(Integer id);

    /**
     * Updates an existing medical history record.
     *
     * @param medicalHistory The MedicalHistory object with updated information.
     * @return The updated MedicalHistory object.
     */
    MedicalHistory updateMedicalHistory(MedicalHistory medicalHistory);

    /**
     * Deletes a medical history record by its ID.
     *
     * @param id The ID of the medical history record to be deleted.
     */
    void deleteMedicalHistory(Integer id);

    /**
     * Retrieves a list of all medical history records.
     *
     * @return A list of all MedicalHistory objects.
     */
    List<MedicalHistory> getAllMedicalHistories();
}