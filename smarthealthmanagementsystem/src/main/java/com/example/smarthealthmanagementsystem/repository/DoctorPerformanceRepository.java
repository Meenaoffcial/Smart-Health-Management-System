package com.example.smarthealthmanagementsystem.repository;

import com.example.smarthealthmanagementsystem.entity.DoctorPerformance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing DoctorPerformance entities.
 * Extends JpaRepository to provide standard JPA operations.
 */
@Repository
public interface DoctorPerformanceRepository extends JpaRepository<DoctorPerformance, Integer> {
    // No additional methods are defined here, as JpaRepository provides basic CRUD operations.
    // If you need custom queries or operations, you can add them here.
}