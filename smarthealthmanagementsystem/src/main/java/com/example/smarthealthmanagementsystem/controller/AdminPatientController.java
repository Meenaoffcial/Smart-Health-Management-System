package com.example.smarthealthmanagementsystem.controller;

import com.example.smarthealthmanagementsystem.entity.Patient;
import com.example.smarthealthmanagementsystem.service.PatientService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/** Controller for managing patient data by administrators. */
@RestController
@RequestMapping("/api/admin/patients")
public class AdminPatientController {

    private static final Logger logger = LoggerFactory.getLogger(AdminPatientController.class);
    private final PatientService patientService;

    /** Constructor to inject PatientService. */
    @Autowired
    public AdminPatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    /** Creates a new patient. */
    @PostMapping
    public ResponseEntity<?> createPatient(@Valid @RequestBody Patient newPatient) {
        logger.info("Admin attempting to add patient: {} {}", newPatient.getFirstName(), newPatient.getLastName());
        Patient createdPatient = patientService.createPatient(newPatient);
        logger.info("Admin successfully added patient with ID: {}", createdPatient.getId());
        return new ResponseEntity<>("Patient added by admin successfully", HttpStatus.CREATED);
    }

    /** Retrieves a patient by their ID. */
    @GetMapping("/{id}")
    public ResponseEntity<?> getPatientById(@PathVariable Integer id) {
        logger.info("Admin attempting to view patient with ID: {}", id);
        Optional<Patient> patientOptional = patientService.getPatientById(id);
        if (patientOptional.isPresent()) {
            logger.info("Admin successfully viewed patient: {} {}", patientOptional.get().getFirstName(), patientOptional.get().getLastName());
            return new ResponseEntity<>(patientOptional.get(), HttpStatus.OK);
        } else {
            logger.warn("Admin failed to view patient with ID {}: Patient not found.", id);
            return new ResponseEntity<>("Error: Patient not found with ID: " + id, HttpStatus.NOT_FOUND);
        }
    }

    /** Retrieves all patients. */
    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        logger.info("Admin attempting to view all patients.");
        List<Patient> patients = patientService.getAllPatients();
        logger.info("Admin successfully viewed {} patients.", patients.size());
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    /** Updates an existing patient's information. */
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePatient(@PathVariable Integer id, @Valid @RequestBody Patient updatedPatient) {
        logger.info("Admin attempting to update patient with ID: {}", id);
        Optional<Patient> existingPatientOptional = patientService.getPatientById(id);
        if (existingPatientOptional.isEmpty()) {
            logger.warn("Admin failed to update patient with ID {}: Patient not found.", id);
            return new ResponseEntity<>("Error: Patient not found with ID: " + id, HttpStatus.NOT_FOUND);
        }
        updatedPatient.setId(id);
        Patient updated = patientService.updatePatient(updatedPatient);
        logger.info("Admin successfully updated patient: {} {}", updated.getFirstName(), updated.getLastName());
        return new ResponseEntity<>("Patient updated by admin successfully", HttpStatus.OK);
    }

    /** Deletes a patient by their ID. */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePatient(@PathVariable Integer id) {
        logger.info("Admin attempting to delete patient with ID: {}", id);
        Optional<Patient> patientOptional = patientService.getPatientById(id);
        if (patientOptional.isEmpty()) {
            logger.warn("Admin failed to delete patient with ID {}: Patient not found.", id);
            return new ResponseEntity<>("Error: Patient not found with ID: " + id, HttpStatus.NOT_FOUND);
        }
        Patient patientToDelete = patientOptional.get();
        patientService.deletePatient(id);
        logger.info("Admin successfully deleted patient: {} {}", patientToDelete.getFirstName(), patientToDelete.getLastName());
        return new ResponseEntity<>("Patient deleted by admin successfully", HttpStatus.NO_CONTENT);
    }
}