package com.example.smarthealthmanagementsystem.repository;

import com.example.smarthealthmanagementsystem.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for managing Appointment entities.
 * Extends JpaRepository to provide standard JPA operations.
 */
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    /**
     * Finds appointments by patient ID.
     *
     * @param patientId The ID of the patient.
     * @return A list of appointments associated with the given patient ID.
     */
    List<Appointment> findByPatientId(Integer patientId);

    /**
     * Finds appointments by doctor ID.
     *
     * @param doctorId The ID of the doctor.
     * @return A list of appointments associated with the given doctor ID.
     */
    List<Appointment> findByDoctorId(Integer doctorId);

    /**
     * Finds appointments within a specified date and time range.
     *
     * @param start The start of the date and time range (inclusive).
     * @param end   The end of the date and time range (exclusive).
     * @return A list of appointments within the given range.
     */
    List<Appointment> findByAppointmentDateTimeBetween(LocalDateTime start, LocalDateTime end);
}