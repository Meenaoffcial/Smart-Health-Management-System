package com.example.smarthealthmanagementsystem.controller;

import com.example.smarthealthmanagementsystem.entity.PatientHealthData;
import com.example.smarthealthmanagementsystem.service.PatientHealthDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

/** Controller for managing patient health data (medical records). */
@RestController
@RequestMapping("/medical-records")
public class PatientHealthDataController {

    private static final Logger logger = LoggerFactory.getLogger(PatientHealthDataController.class);

    private final PatientHealthDataService patientHealthDataService;

    /** Constructor to inject PatientHealthDataService. */
    @Autowired
    public PatientHealthDataController(PatientHealthDataService patientHealthDataService) {
        this.patientHealthDataService = patientHealthDataService;
    }

    /** Creates a new medical record. */
    @PostMapping
    public ResponseEntity<String> createMedicalRecord(@RequestBody PatientHealthData record) {
        logger.info("Attempting to create medical record: {}", record);
        PatientHealthData createdRecord = patientHealthDataService.createMedicalRecord(record);
        logger.info("Medical record created with ID: {}", createdRecord.getId());
        return new ResponseEntity<>("Medical record created with ID: " + createdRecord.getId(), HttpStatus.CREATED);
    }

    /** Retrieves a medical record by its ID. */
    @GetMapping("/{id}")
    public ResponseEntity<?> getMedicalRecord(@PathVariable int id) {
        logger.info("Attempting to retrieve medical record with ID: {}", id);
        Optional<PatientHealthData> record = patientHealthDataService.getMedicalRecord(id);
        if (record.isPresent()) {
            logger.info("Medical record found with ID: {}", id);
            return new ResponseEntity<>(record.get(), HttpStatus.OK);
        } else {
            logger.warn("Medical record not found with ID: {}", id);
            return new ResponseEntity<>("Medical record not found", HttpStatus.NOT_FOUND);
        }
    }

    /** Retrieves all medical records. */
    @GetMapping
    public ResponseEntity<List<PatientHealthData>> getAllMedicalRecords() {
        logger.info("Attempting to retrieve all medical records.");
        List<PatientHealthData> records = patientHealthDataService.getAllMedicalRecords();
        logger.info("Retrieved {} medical records.", records.size());
        return new ResponseEntity<>(records, HttpStatus.OK);
    }

    /** Updates an existing medical record. */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateMedicalRecord(@PathVariable int id, @RequestBody PatientHealthData record) {
        logger.info("Attempting to update medical record with ID: {}, data: {}", id, record);
        PatientHealthData updatedRecord = patientHealthDataService.updateMedicalRecord(id, record);
        logger.info("Medical record updated with ID: {}", updatedRecord.getId());
        return new ResponseEntity<>("Medical record updated with ID: " + updatedRecord.getId(), HttpStatus.OK);
    }

    // No delete method to prevent deletion
}