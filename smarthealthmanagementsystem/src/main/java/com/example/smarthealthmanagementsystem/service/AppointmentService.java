package com.example.smarthealthmanagementsystem.service;

import com.example.smarthealthmanagementsystem.entity.Appointment;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing appointments.
 * Defines methods for booking, rescheduling, cancelling, and retrieving appointments.
 */
public interface AppointmentService {

    /**
     * Books a new appointment.
     *
     * @param appointment The Appointment object to be booked.
     * @return The booked Appointment object.
     */
    Appointment bookAppointment(Appointment appointment);

    /**
     * Reschedules an existing appointment.
     *
     * @param id          The ID of the appointment to be rescheduled.
     * @param appointment The updated Appointment object.
     * @return An Optional containing the rescheduled Appointment, or an empty Optional if not found.
     */
    Optional<Appointment> rescheduleAppointment(Integer id, Appointment appointment);

    /**
     * Cancels an appointment.
     *
     * @param id The ID of the appointment to be cancelled.
     */
    void cancelAppointment(Integer id);

    /**
     * Retrieves an appointment by its ID.
     *
     * @param id The ID of the appointment.
     * @return An Optional containing the Appointment object, or an empty Optional if not found.
     */
    Optional<Appointment> getAppointmentById(Integer id);

    /**
     * Retrieves a list of appointments for a specific patient.
     *
     * @param patientId The ID of the patient.
     * @return A list of Appointment objects.
     */
    List<Appointment> getAppointmentsByPatientId(Integer patientId);

    /**
     * Retrieves a list of appointments for a specific doctor.
     *
     * @param doctorId The ID of the doctor.
     * @return A list of Appointment objects.
     */
    List<Appointment> getAppointmentsByDoctorId(Integer doctorId);

    /**
     * Sends an appointment confirmation email to the patient.
     *
     * @param appointment   The Appointment object for which the email is sent.
     * @param patientEmail  The email address of the patient.
     */
    void sendAppointmentConfirmationEmail(Appointment appointment, String patientEmail);
}