package com.example.smarthealthmanagementsystem.service;

/**
 * Service interface for sending emails.
 * Defines methods for sending various types of emails.
 */
public interface EmailService {

    /**
     * Sends an appointment reminder email.
     *
     * @param to      The recipient's email address.
     * @param subject The subject of the email.
     * @param text    The body of the email.
     */
    void sendAppointmentReminder(String to, String subject, String text);
}