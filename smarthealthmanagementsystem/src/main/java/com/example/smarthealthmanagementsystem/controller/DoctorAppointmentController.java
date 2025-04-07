package com.example.smarthealthmanagementsystem.controller;

import com.example.smarthealthmanagementsystem.entity.Appointment;
import com.example.smarthealthmanagementsystem.service.AppointmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/** Controller for doctors to manage their appointments. */
@RestController
@RequestMapping("/api/doctors/appointments")
public class DoctorAppointmentController {

    private final AppointmentService appointmentService;
    private static final Logger logger = LoggerFactory.getLogger(DoctorAppointmentController.class);

    /** Constructor to inject AppointmentService. */
    @Autowired
    public DoctorAppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    /** Retrieves an appointment by its ID. */
    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Integer id) {
        logger.info("Doctor attempting to view appointment with ID: {}", id);
        Optional<Appointment> appointment = appointmentService.getAppointmentById(id);
        return appointment.map(appointmentResponseEntity -> {
            logger.info("Appointment found with ID: {}", id);
            return ResponseEntity.ok(appointmentResponseEntity);
        }).orElseGet(() -> {
            logger.warn("Appointment not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        });
    }

    /** Retrieves appointments for a specific patient. */
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByPatientId(@PathVariable Integer patientId) {
        logger.info("Doctor attempting to view appointments for patient ID: {}", patientId);
        List<Appointment> appointments = appointmentService.getAppointmentsByPatientId(patientId);
        logger.info("Found {} appointments for patient ID: {}", appointments.size(), patientId);
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    /** Retrieves appointments for a specific doctor. */
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByDoctorId(@PathVariable Integer doctorId) {
        logger.info("Doctor attempting to view appointments for doctor ID: {}", doctorId);
        List<Appointment> appointments = appointmentService.getAppointmentsByDoctorId(doctorId);
        logger.info("Found {} appointments for doctor ID: {}", appointments.size(), doctorId);
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }
}