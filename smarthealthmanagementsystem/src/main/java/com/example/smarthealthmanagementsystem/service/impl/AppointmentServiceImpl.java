package com.example.smarthealthmanagementsystem.service.impl;

import com.example.smarthealthmanagementsystem.entity.Appointment;
import com.example.smarthealthmanagementsystem.entity.Patient;
import com.example.smarthealthmanagementsystem.entity.User;
import com.example.smarthealthmanagementsystem.repository.AppointmentRepository;
import com.example.smarthealthmanagementsystem.repository.PatientRepository;
import com.example.smarthealthmanagementsystem.repository.UserRepository;
import com.example.smarthealthmanagementsystem.service.AppointmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the AppointmentService interface.
 * Provides methods for managing appointments, including booking, rescheduling, and sending confirmation emails.
 */
@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final JavaMailSender javaMailSender;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(AppointmentServiceImpl.class);

    /**
     * Constructor to inject dependencies.
     *
     * @param appointmentRepository Repository for Appointment entities.
     * @param javaMailSender        JavaMailSender for sending emails.
     * @param patientRepository     Repository for Patient entities.
     * @param userRepository        Repository for User entities.
     */
    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, JavaMailSender javaMailSender, PatientRepository patientRepository, UserRepository userRepository) {
        this.appointmentRepository = appointmentRepository;
        this.javaMailSender = javaMailSender;
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
    }

    /**
     * Books a new appointment and sends a confirmation email.
     *
     * @param appointment The Appointment object to be booked.
     * @return The booked Appointment object.
     */
    @Override
    public Appointment bookAppointment(Appointment appointment) {
        logger.debug("Booking appointment: {}", appointment);
        Appointment savedAppointment = appointmentRepository.save(appointment);
        String patientEmail = getPatientEmail(appointment.getPatientId());
        sendAppointmentConfirmationEmail(savedAppointment, patientEmail);
        return savedAppointment;
    }

    /**
     * Reschedules an existing appointment.
     *
     * @param id          The ID of the appointment to be rescheduled.
     * @param appointment The updated Appointment object.
     * @return An Optional containing the rescheduled Appointment, or an empty Optional if not found.
     */
    @Override
    public Optional<Appointment> rescheduleAppointment(Integer id, Appointment appointment) {
        logger.debug("Rescheduling appointment with ID: {}, new data: {}", id, appointment);
        Optional<Appointment> existingAppointment = appointmentRepository.findById(id);
        if (existingAppointment.isPresent()) {
            appointment.setId(id);
            Appointment updatedAppointment = appointmentRepository.save(appointment);
            return Optional.of(updatedAppointment);
        }
        return Optional.empty();
    }

    /**
     * Cancels an appointment.
     *
     * @param id The ID of the appointment to be cancelled.
     */
    @Override
    public void cancelAppointment(Integer id) {
        logger.debug("Cancelling appointment with ID: {}", id);
        appointmentRepository.deleteById(id);
    }

    /**
     * Retrieves an appointment by its ID.
     *
     * @param id The ID of the appointment.
     * @return An Optional containing the Appointment object, or an empty Optional if not found.
     */
    @Override
    public Optional<Appointment> getAppointmentById(Integer id) {
        logger.debug("Getting appointment by ID: {}", id);
        return appointmentRepository.findById(id);
    }

    /**
     * Retrieves a list of appointments for a specific patient.
     *
     * @param patientId The ID of the patient.
     * @return A list of Appointment objects.
     */
    @Override
    public List<Appointment> getAppointmentsByPatientId(Integer patientId) {
        logger.debug("Getting appointments by patient ID: {}", patientId);
        return appointmentRepository.findByPatientId(patientId);
    }

    /**
     * Retrieves a list of appointments for a specific doctor.
     *
     * @param doctorId The ID of the doctor.
     * @return A list of Appointment objects.
     */
    @Override
    public List<Appointment> getAppointmentsByDoctorId(Integer doctorId) {
        logger.debug("Getting appointments by doctor ID: {}", doctorId);
        return appointmentRepository.findByDoctorId(doctorId);
    }

    /**
     * Sends an appointment confirmation email to the patient.
     *
     * @param appointment  The Appointment object for which the email is sent.
     * @param patientEmail The email address of the patient.
     */
    @Override
    public void sendAppointmentConfirmationEmail(Appointment appointment, String patientEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(patientEmail);
        message.setSubject("Appointment Confirmation");

        Optional<User> doctor = userRepository.findById(appointment.getDoctorId());
        String doctorName = doctor.map(User::getUsername).orElse("Unknown Doctor");

        message.setText("Your appointment is scheduled for " + appointment.getAppointmentDateTime() +
                " with Dr. " + doctorName + "." +
                " Reason: " + appointment.getReason() + ". Notes: " + appointment.getNotes());

        try {
            javaMailSender.send(message);
            logger.info("Appointment confirmation email sent for appointment ID: {}", appointment.getId());
        } catch (Exception e) {
            logger.error("Failed to send appointment confirmation email: {}", e.getMessage(), e);
        }
    }

    /**
     * Retrieves the email address of a patient.
     *
     * @param patientId The ID of the patient.
     * @return The email address of the patient, or a default email if not found.
     */
    private String getPatientEmail(int patientId) {
        Optional<Patient> patient = patientRepository.findById(patientId);

        if (patient.isPresent()) {
            return patient.get().getEmail();
        } else {
            logger.warn("Patient with ID {} not found. Using default email.", patientId);
            return "default@example.com";
        }
    }
}