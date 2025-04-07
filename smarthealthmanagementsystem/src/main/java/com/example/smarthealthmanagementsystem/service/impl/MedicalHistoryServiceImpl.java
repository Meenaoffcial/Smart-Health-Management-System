package com.example.smarthealthmanagementsystem.service.impl;

import com.example.smarthealthmanagementsystem.entity.MedicalHistory;
import com.example.smarthealthmanagementsystem.repository.MedicalHistoryRepository;
import com.example.smarthealthmanagementsystem.service.MedicalHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the MedicalHistoryService interface.
 * Provides methods for managing patient medical history records.
 */
@Service
public class MedicalHistoryServiceImpl implements MedicalHistoryService {

    private final MedicalHistoryRepository medicalHistoryRepository;

    /**
     * Constructor to inject MedicalHistoryRepository.
     *
     * @param medicalHistoryRepository Repository for MedicalHistory entities.
     */
    @Autowired
    public MedicalHistoryServiceImpl(MedicalHistoryRepository medicalHistoryRepository) {
        this.medicalHistoryRepository = medicalHistoryRepository;
    }

    /**
     * Retrieves a list of medical history records for a specific patient.
     *
     * @param patientId The ID of the patient.
     * @return A list of MedicalHistory objects associated with the patient.
     */
    @Override
    public List<MedicalHistory> getMedicalHistoryByPatientId(Integer patientId) {
        return medicalHistoryRepository.findByPatientId(patientId);
    }

    /**
     * Adds a new medical history record.
     *
     * @param medicalHistory The MedicalHistory object to be added.
     * @return The added MedicalHistory object.
     */
    @Override
    public MedicalHistory addMedicalHistory(MedicalHistory medicalHistory) {
        return medicalHistoryRepository.save(medicalHistory);
    }

    /**
     * Retrieves a medical history record by its ID.
     *
     * @param id The ID of the medical history record.
     * @return An Optional containing the MedicalHistory object, or an empty Optional if not found.
     */
    @Override
    public Optional<MedicalHistory> getMedicalHistoryById(Integer id) {
        return medicalHistoryRepository.findById(id);
    }

    /**
     * Updates an existing medical history record.
     *
     * @param medicalHistory The MedicalHistory object with updated information.
     * @return The updated MedicalHistory object.
     */
    @Override
    public MedicalHistory updateMedicalHistory(MedicalHistory medicalHistory) {
        return medicalHistoryRepository.save(medicalHistory);
    }

    /**
     * Deletes a medical history record by its ID.
     *
     * @param id The ID of the medical history record to be deleted.
     */
    @Override
    public void deleteMedicalHistory(Integer id) {
        medicalHistoryRepository.deleteById(id);
    }

    /**
     * Retrieves a list of all medical history records.
     *
     * @return A list of all MedicalHistory objects.
     */
    @Override
    public List<MedicalHistory> getAllMedicalHistories() {
        return medicalHistoryRepository.findAll();
    }
}