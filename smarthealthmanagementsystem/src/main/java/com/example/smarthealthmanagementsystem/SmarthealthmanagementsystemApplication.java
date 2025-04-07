package com.example.smarthealthmanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;

/** Main application class for Smart Health Management System. */
@SpringBootApplication
public class SmarthealthmanagementsystemApplication {

    private static final Logger logger = LoggerFactory.getLogger(SmarthealthmanagementsystemApplication.class);

    /** Main method to start the Spring Boot application. */
    public static void main(String[] args) {
        logger.info("Starting Smart Health Management System application...");
        ConfigurableApplicationContext context = SpringApplication.run(SmarthealthmanagementsystemApplication.class, args);
        logger.info("Smart Health Management System application started successfully.");

        // Optional post-startup tasks.
        if (context.isActive()) {
            logger.debug("Application context is active."); // Debug: context active
        } else {
            logger.warn("Application context is not active."); // Warn: context inactive
        }
    }
}