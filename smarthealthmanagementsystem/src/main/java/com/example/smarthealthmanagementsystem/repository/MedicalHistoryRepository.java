package com.example.smarthealthmanagementsystem.repository;

import com.example.smarthealthmanagementsystem.entity.MedicalHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing MedicalHistory entities.
 * Extends JpaRepository to provide standard JPA operations.
 */
@Repository
public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Integer> {

    /**
     * Finds medical history records by patient ID.
     *
     * @param patientId The ID of the patient.
     * @return A list of MedicalHistory objects associated with the given patient ID.
     */
    List<MedicalHistory> findByPatientId(Integer patientId);
}