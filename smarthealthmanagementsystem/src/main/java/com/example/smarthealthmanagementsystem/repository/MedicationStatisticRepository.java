package com.example.smarthealthmanagementsystem.repository;

import com.example.smarthealthmanagementsystem.entity.MedicationStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationStatisticRepository extends JpaRepository<MedicationStatistic, Integer> {
}