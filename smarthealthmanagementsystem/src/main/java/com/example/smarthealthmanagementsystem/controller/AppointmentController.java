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

/** Controller for managing patient appointments by administrators. */
@RestController
@RequestMapping("/api/admin/patients/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private static final Logger logger = LoggerFactory.getLogger(AppointmentController.class);

    /** Constructor to inject AppointmentService. */
    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    /** Books a new appointment. */
    @PostMapping
    public ResponseEntity<String> bookAppointment(@RequestBody Appointment appointment) {
        logger.debug("Received request to book appointment: {}", appointment);
        try {
            Appointment bookedAppointment = appointmentService.bookAppointment(appointment);
            int appointmentId = bookedAppointment.getId();
            logger.info("Appointment booked successfully with ID: {}", appointmentId);
            return new ResponseEntity<>("Appointment booked successfully with ID: " + appointmentId +
                    ". Confirmation email sent to patient.", HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error booking appointment: {}", e.getMessage(), e);
            return new ResponseEntity<>("Failed to book appointment: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /** Reschedules an existing appointment. */
    @PutMapping("/{id}")
    public ResponseEntity<String> rescheduleAppointment(@PathVariable Integer id, @RequestBody Appointment appointment) {
        logger.debug("Received request to reschedule appointment with ID: {}, new data: {}", id, appointment);
        Optional<Appointment> rescheduledAppointment = appointmentService.rescheduleAppointment(id, appointment);
        if (rescheduledAppointment.isPresent()) {
            logger.info("Appointment rescheduled successfully with ID: {}", rescheduledAppointment.get().getId());
            return new ResponseEntity<>("Appointment rescheduled successfully with ID: " + id, HttpStatus.OK);
        } else {
            logger.warn("Appointment not found for rescheduling with ID: {}", id);
            return new ResponseEntity<>("Appointment not found with ID: " + id, HttpStatus.NOT_FOUND);
        }
    }

    /** Cancels an existing appointment. */
    @PutMapping("/{id}/cancel")
    public ResponseEntity<String> cancelAppointment(@PathVariable Integer id) {
        logger.debug("Received request to cancel appointment with ID: {}", id);
        Optional<Appointment> appointment = appointmentService.getAppointmentById(id);
        if (appointment.isPresent()) {
            Appointment canceledAppointment = appointment.get();
            canceledAppointment.setStatus("Cancelled");
            appointmentService.bookAppointment(canceledAppointment);
            logger.info("Appointment cancelled successfully with ID: {}", id);
            return new ResponseEntity<>("Appointment cancelled successfully with ID: " + id, HttpStatus.OK);
        } else {
            logger.warn("Appointment not found for cancellation with ID: {}", id);
            return new ResponseEntity<>("Appointment not found with ID: " + id, HttpStatus.NOT_FOUND);
        }
    }

    /** Retrieves an appointment by its ID. */
    @GetMapping("/{id}")
    public ResponseEntity<String> getAppointmentById(@PathVariable Integer id) {
        logger.debug("Received request to get appointment with ID: {}", id);
        Optional<Appointment> appointment = appointmentService.getAppointmentById(id);
        if (appointment.isPresent()) {
            logger.info("Appointment found with ID: {}", appointment.get().getId());
            return new ResponseEntity<>("Appointment found with ID: " + id, HttpStatus.OK);
        } else {
            logger.warn("Appointment not found with ID: {}", id);
            return new ResponseEntity<>("Appointment not found with ID: " + id, HttpStatus.NOT_FOUND);
        }
    }

    /** Retrieves appointments for a specific patient. */
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<String> getAppointmentsByPatient(@PathVariable Integer patientId) {
        logger.debug("Received request to get appointments by patient ID: {}", patientId);
        List<Appointment> appointments = appointmentService.getAppointmentsByPatientId(patientId);
        logger.info("Appointments found for patient ID {}. Count: {}", patientId, appointments.size());
        return new ResponseEntity<>("Appointments found for patient ID: " + patientId + ". Count: " + appointments.size(), HttpStatus.OK);
    }

    /** Retrieves appointments for a specific doctor. */
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<String> getAppointmentsByDoctor(@PathVariable Integer doctorId) {
        logger.debug("Received request to get appointments by doctor ID: {}", doctorId);
        List<Appointment> appointments = appointmentService.getAppointmentsByDoctorId(doctorId);
        logger.info("Appointments found for doctor ID {}. Count: {}", doctorId, appointments.size());
        return new ResponseEntity<>("Appointments found for doctor ID: " + doctorId + ". Count: " + appointments.size(), HttpStatus.OK);
    }
}