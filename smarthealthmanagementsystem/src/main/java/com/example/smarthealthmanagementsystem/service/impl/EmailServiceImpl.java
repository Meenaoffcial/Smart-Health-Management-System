package com.example.smarthealthmanagementsystem.service.impl;

import com.example.smarthealthmanagementsystem.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Implementation of the EmailService interface.
 * Provides methods for sending emails, such as appointment reminders.
 */
@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    /**
     * Constructor to inject JavaMailSender.
     *
     * @param mailSender JavaMailSender for sending emails.
     */
    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Sends an appointment reminder email.
     *
     * @param to      The recipient's email address.
     * @param subject The subject of the email.
     * @param text    The body of the email.
     */
    @Override
    public void sendAppointmentReminder(String to, String subject, String text) {
        logger.debug("Sending email to: {}, subject: {}", to, subject);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        try {
            mailSender.send(message);
            logger.info("Email sent successfully to: {}", to);
        } catch (Exception e) {
            logger.error("Failed to send email to: {}, error: {}", to, e.getMessage(), e);
        }

    }
}