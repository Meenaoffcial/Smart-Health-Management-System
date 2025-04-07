package com.example.smarthealthmanagementsystem.controller;

import com.example.smarthealthmanagementsystem.entity.MedicalHistory;
import com.example.smarthealthmanagementsystem.service.MedicalHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Controller for managing patient medical histories by nurses in the admin context. */
@RestController
@RequestMapping("/api/admin/nurse/patient/medical-history")
public class AdminNursePatientMedicalHistoryController {

    private final MedicalHistoryService medicalHistoryService;
    private static final Logger logger = LoggerFactory.getLogger(AdminNursePatientMedicalHistoryController.class);

    /** Constructor to inject MedicalHistoryService. */
    @Autowired
    public AdminNursePatientMedicalHistoryController(MedicalHistoryService medicalHistoryService) {
        this.medicalHistoryService = medicalHistoryService;
    }

    /** Retrieves medical histories for a specific patient. */
    @GetMapping("/{patientId}")
    public ResponseEntity<List<MedicalHistory>> getMedicalHistoriesByPatient(@PathVariable Integer patientId) {
        logger.info("Retrieving medical histories for patient ID: {}", patientId);
        List<MedicalHistory> histories = medicalHistoryService.getMedicalHistoryByPatientId(patientId);
        logger.info("Found {} medical histories for patient ID: {}", histories.size(), patientId);
        return new ResponseEntity<>(histories, HttpStatus.OK);
    }

    /** Adds a new medical history entry. */
    @PostMapping
    public ResponseEntity<MedicalHistory> addMedicalHistory(@RequestBody MedicalHistory medicalHistory) {
        logger.info("Adding new medical history: {}", medicalHistory);
        MedicalHistory addedHistory = medicalHistoryService.addMedicalHistory(medicalHistory);
        logger.info("Medical history added successfully with ID: {}", addedHistory.getId());
        return new ResponseEntity<>(addedHistory, HttpStatus.CREATED);
    }

    /** Updates an existing medical history entry. */
    @PutMapping("/{id}")
    public ResponseEntity<MedicalHistory> updateMedicalHistory(@PathVariable Integer id, @RequestBody MedicalHistory medicalHistory) {
        logger.info("Updating medical history with ID: {}, data: {}", id, medicalHistory);
        medicalHistory.setId(id);
        MedicalHistory updatedHistory = medicalHistoryService.updateMedicalHistory(medicalHistory);
        logger.info("Medical history updated successfully with ID: {}", updatedHistory.getId());
        return new ResponseEntity<>(updatedHistory, HttpStatus.OK);
    }

    /** Deletes a medical history entry by ID. */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicalHistory(@PathVariable Integer id) {
        logger.info("Deleting medical history with ID: {}", id);
        medicalHistoryService.deleteMedicalHistory(id);
        logger.info("Medical history deleted successfully with ID: {}", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}