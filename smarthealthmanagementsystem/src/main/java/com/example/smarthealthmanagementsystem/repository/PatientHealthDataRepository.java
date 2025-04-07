package com.example.smarthealthmanagementsystem.repository;

import com.example.smarthealthmanagementsystem.entity.PatientHealthData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing PatientHealthData entities.
 * Extends JpaRepository to provide standard JPA operations.
 */
@Repository
public interface PatientHealthDataRepository extends JpaRepository<PatientHealthData, Integer> {
    // No additional methods are defined here, as JpaRepository provides basic CRUD operations.
    // If you need custom queries or operations, you can add them here.
}