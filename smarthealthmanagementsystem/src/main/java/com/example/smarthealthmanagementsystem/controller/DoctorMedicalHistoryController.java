package com.example.smarthealthmanagementsystem.controller;

import com.example.smarthealthmanagementsystem.entity.MedicalHistory;
import com.example.smarthealthmanagementsystem.service.MedicalHistoryService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

/** Controller for doctors to manage patient medical histories. */
@RestController
@RequestMapping("/api/doctors/patients/medical-history")
public class DoctorMedicalHistoryController {

    private static final Logger logger = LoggerFactory.getLogger(DoctorMedicalHistoryController.class);

    private final MedicalHistoryService medicalHistoryService;

    /** Constructor to inject MedicalHistoryService. */
    @Autowired
    public DoctorMedicalHistoryController(MedicalHistoryService medicalHistoryService) {
        this.medicalHistoryService = medicalHistoryService;
    }

    /** Adds a new medical history entry. */
    @PostMapping
    public ResponseEntity<Object> addMedicalHistory(@Valid @RequestBody MedicalHistory medicalHistory) {
        logger.info("Adding medical history: {}", medicalHistory);
        MedicalHistory addedHistory = medicalHistoryService.addMedicalHistory(medicalHistory);
        logger.info("Medical history added successfully: {}", addedHistory);
        return new ResponseEntity<>(
                "Medical history added successfully: " + addedHistory.getId(),
                HttpStatus.CREATED);
    }

    /** Retrieves a medical history entry by its ID. */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getMedicalHistoryById(@PathVariable Integer id) {
        logger.info("Getting medical history by ID: {}", id);
        Optional<MedicalHistory> history = medicalHistoryService.getMedicalHistoryById(id);
        if (history.isPresent()) {
            logger.info("Medical history found: {}", history.get());
            return new ResponseEntity<>(history.get(), HttpStatus.OK);
        } else {
            logger.warn("Medical history not found for ID: {}", id);
            return new ResponseEntity<>("Medical history not found", HttpStatus.NOT_FOUND);
        }
    }

    /** Updates an existing medical history entry. */
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateMedicalHistory(@PathVariable Integer id, @Valid @RequestBody MedicalHistory medicalHistory) {
        logger.info("Updating medical history with ID: {}, data: {}", id, medicalHistory);
        try {
            Optional<MedicalHistory> existingMedicalHistory = medicalHistoryService.getMedicalHistoryById(id);

            if (existingMedicalHistory.isPresent()) {
                medicalHistory.setId(id);
                MedicalHistory updatedHistory = medicalHistoryService.updateMedicalHistory(medicalHistory);
                logger.info("Medical history updated successfully: {}", updatedHistory);
                return new ResponseEntity<>("Medical history updated successfully", HttpStatus.OK);
            } else {
                logger.warn("Medical history not found for update with ID: {}", id);
                return new ResponseEntity<>("Medical history not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error updating medical history: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update medical history", e);
        }
    }

    /** Deletes a medical history entry by its ID. */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteMedicalHistory(@PathVariable Integer id) {
        logger.info("Deleting medical history with ID: {}", id);
        medicalHistoryService.deleteMedicalHistory(id);
        logger.info("Medical history deleted successfully with ID: {}", id);
        return new ResponseEntity<>("Medical history deleted successfully", HttpStatus.NO_CONTENT);
    }

    /** Retrieves all medical history entries for a specific patient. */
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<MedicalHistory>> getAllMedicalHistoryByPatientId(@PathVariable Integer patientId) {
        logger.info("Getting all medical histories for patient ID: {}", patientId);
        List<MedicalHistory> histories = medicalHistoryService.getMedicalHistoryByPatientId(patientId);
        logger.info("Medical histories found for patient ID {}: {}", patientId, histories);
        return new ResponseEntity<>(histories, HttpStatus.OK);
    }

    /** Retrieves all medical history entries. */
    @GetMapping
    public ResponseEntity<List<MedicalHistory>> getAllMedicalHistories() {
        logger.info("Getting all medical histories.");
        List<MedicalHistory> histories = medicalHistoryService.getAllMedicalHistories();
        logger.info("All medical histories found.");
        return new ResponseEntity<>(histories, HttpStatus.OK);
    }
}