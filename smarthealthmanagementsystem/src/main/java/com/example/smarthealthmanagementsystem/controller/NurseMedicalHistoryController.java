package com.example.smarthealthmanagementsystem.controller;

import com.example.smarthealthmanagementsystem.entity.MedicalHistory;
import com.example.smarthealthmanagementsystem.service.MedicalHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Controller for nurses to manage patient medical histories. */
@RestController
@RequestMapping("/api/nurse/medical-history")
public class NurseMedicalHistoryController {

    private final MedicalHistoryService medicalHistoryService;
    private static final Logger logger = LoggerFactory.getLogger(NurseMedicalHistoryController.class);

    /** Constructor to inject MedicalHistoryService. */
    @Autowired
    public NurseMedicalHistoryController(MedicalHistoryService medicalHistoryService) {
        this.medicalHistoryService = medicalHistoryService;
    }

    /** Adds a new medical history entry. */
    @PostMapping
    public ResponseEntity<MedicalHistory> addMedicalHistory(@RequestBody MedicalHistory medicalHistory) {
        logger.info("Nurse attempting to add medical history: {}", medicalHistory);
        MedicalHistory addedHistory = medicalHistoryService.addMedicalHistory(medicalHistory);
        logger.info("Medical history added successfully: {}", addedHistory);
        return new ResponseEntity<>(addedHistory, HttpStatus.CREATED);
    }

    /** Updates an existing medical history entry. */
    @PutMapping("/{id}")
    public ResponseEntity<MedicalHistory> updateMedicalHistory(@PathVariable Integer id, @RequestBody MedicalHistory medicalHistory) {
        logger.info("Nurse attempting to update medical history with ID: {}, data: {}", id, medicalHistory);
        medicalHistory.setId(id);
        MedicalHistory updatedHistory = medicalHistoryService.updateMedicalHistory(medicalHistory);
        logger.info("Medical history updated successfully: {}", updatedHistory);
        return new ResponseEntity<>(updatedHistory, HttpStatus.OK);
    }
}