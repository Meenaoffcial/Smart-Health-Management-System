package com.example.smarthealthmanagementsystem.controller;

import com.example.smarthealthmanagementsystem.entity.MedicalHistory;
import com.example.smarthealthmanagementsystem.service.MedicalHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Controller for patients to access their medical history. */
@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final MedicalHistoryService medicalHistoryService;
    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

    /** Constructor to inject MedicalHistoryService. */
    @Autowired
    public PatientController(MedicalHistoryService medicalHistoryService) {
        this.medicalHistoryService = medicalHistoryService;
    }

    /** Retrieves the medical history for a specific patient. */
    @GetMapping("/{patientId}/medical-history")
    public ResponseEntity<List<MedicalHistory>> getPatientMedicalHistory(@PathVariable Integer patientId) {
        logger.info("Patient attempting to view medical history for patient ID: {}", patientId);
        List<MedicalHistory> medicalHistory = medicalHistoryService.getMedicalHistoryByPatientId(patientId);
        logger.info("Found {} medical history entries for patient ID: {}", medicalHistory.size(), patientId);
        return new ResponseEntity<>(medicalHistory, HttpStatus.OK);
    }
}