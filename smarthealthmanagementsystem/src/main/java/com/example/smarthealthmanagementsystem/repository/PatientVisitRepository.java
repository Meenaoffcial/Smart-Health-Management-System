package com.example.smarthealthmanagementsystem.repository;

import com.example.smarthealthmanagementsystem.entity.PatientVisit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientVisitRepository extends JpaRepository<PatientVisit, Integer> {
}