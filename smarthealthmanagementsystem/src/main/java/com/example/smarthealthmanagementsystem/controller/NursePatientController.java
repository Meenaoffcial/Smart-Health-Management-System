package com.example.smarthealthmanagementsystem.controller;

import com.example.smarthealthmanagementsystem.entity.Patient;
import com.example.smarthealthmanagementsystem.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/** Controller for nurses to view patient data. */
@RestController
@RequestMapping("/api/admin/nurses/patients")
@PreAuthorize("hasRole('NURSE')")
public class NursePatientController {

    private static final Logger logger = LoggerFactory.getLogger(NursePatientController.class);
    private final PatientService patientService;

    /** Constructor to inject PatientService. */
    @Autowired
    public NursePatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    /** Retrieves a patient by their ID. */
    @GetMapping("/{id}")
    public ResponseEntity<?> getPatientById(@PathVariable Integer id) {
        logger.info("Nurse attempting to view patient with ID: {}", id);
        Optional<Patient> patientOptional = patientService.getPatientById(id);
        if (patientOptional.isPresent()) {
            logger.info("Nurse successfully viewed patient: {} {}", patientOptional.get().getFirstName(), patientOptional.get().getLastName());
            return new ResponseEntity<>(patientOptional.get(), HttpStatus.OK);
        } else {
            logger.warn("Nurse failed to view patient with ID {}: Patient not found.", id);
            return new ResponseEntity<>("Error: Patient not found with ID: " + id, HttpStatus.NOT_FOUND);
        }
    }

    /** Retrieves all patients. */
    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        logger.info("Nurse attempting to view all patients.");
        List<Patient> patients = patientService.getAllPatients();
        logger.info("Nurse successfully viewed {} patients.", patients.size());
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }
}