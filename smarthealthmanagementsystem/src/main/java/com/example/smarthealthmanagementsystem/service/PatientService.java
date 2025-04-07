package com.example.smarthealthmanagementsystem.service;

import com.example.smarthealthmanagementsystem.entity.Patient;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing patient data.
 * Defines methods for creating, retrieving, updating, and deleting patient records.
 */
public interface PatientService {

    /**
     * Creates a new patient record.
     *
     * @param patient The Patient object to be created.
     * @return The created Patient object.
     */
    Patient createPatient(Patient patient);

    /**
     * Retrieves a list of all patient records.
     *
     * @return A list of Patient objects.
     */
    List<Patient> getAllPatients();

    /**
     * Retrieves a patient record by its ID.
     *
     * @param id The ID of the patient record.
     * @return An Optional containing the Patient object, or an empty Optional if not found.
     */
    Optional<Patient> getPatientById(Integer id);

    /**
     * Updates an existing patient record.
     *
     * @param patient The Patient object with updated information.
     * @return The updated Patient object.
     */
    Patient updatePatient(Patient patient);

    /**
     * Deletes a patient record by its ID.
     *
     * @param id The ID of the patient record to be deleted.
     */
    void deletePatient(Integer id);
}