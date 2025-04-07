package com.example.smarthealthmanagementsystem.controller;

import com.example.smarthealthmanagementsystem.entity.Appointment;
import com.example.smarthealthmanagementsystem.service.AppointmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/** Controller for nurses to manage appointments. */
@RestController
@RequestMapping("/api/nurse/appointments")
public class NurseAppointmentController {

    private final AppointmentService appointmentService;
    private static final Logger logger = LoggerFactory.getLogger(NurseAppointmentController.class);

    /** Constructor to inject AppointmentService. */
    @Autowired
    public NurseAppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    /** Adds a new appointment. */
    @PostMapping
    public ResponseEntity<Appointment> addAppointment(@RequestBody Appointment appointment) {
        logger.debug("Received request to add appointment: {}", appointment);
        Appointment addedAppointment = appointmentService.bookAppointment(appointment); // Use bookAppointment
        logger.info("Appointment added successfully with ID: {}", addedAppointment.getId());
        return new ResponseEntity<>(addedAppointment, HttpStatus.CREATED);
    }

    /** Updates the status of an existing appointment. */
    @PutMapping("/{id}/status")
    public ResponseEntity<Appointment> updateAppointmentStatus(@PathVariable Integer id, @RequestBody String status) {
        logger.debug("Received request to update appointment status with ID: {}, new status: {}", id, status);
        Optional<Appointment> appointment = appointmentService.getAppointmentById(id);
        if (appointment.isPresent()) {
            Appointment updatedAppointment = appointment.get();
            updatedAppointment.setStatus(status);
            Appointment savedAppointment = appointmentService.bookAppointment(updatedAppointment); // Use bookAppointment
            logger.info("Appointment status updated successfully: {}", savedAppointment);
            return new ResponseEntity<>(savedAppointment, HttpStatus.OK);
        } else {
            logger.warn("Appointment not found for status update with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}