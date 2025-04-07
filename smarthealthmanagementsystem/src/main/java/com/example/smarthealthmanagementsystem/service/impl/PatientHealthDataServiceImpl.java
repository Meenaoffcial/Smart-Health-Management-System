package com.example.smarthealthmanagementsystem.service.impl;

import com.example.smarthealthmanagementsystem.entity.PatientHealthData;
import com.example.smarthealthmanagementsystem.repository.PatientHealthDataRepository;
import com.example.smarthealthmanagementsystem.service.PatientHealthDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PatientHealthDataServiceImpl implements PatientHealthDataService {

    private static final Logger logger = LoggerFactory.getLogger(PatientHealthDataServiceImpl.class);

    @Autowired
    private PatientHealthDataRepository patientHealthDataRepository;

    @Override
    public PatientHealthData createMedicalRecord(PatientHealthData record) {
        logger.info("Creating medical record: {}", record);
        return patientHealthDataRepository.save(record);
    }

    @Override
    public Optional<PatientHealthData> getMedicalRecord(int id) {
        logger.info("Retrieving medical record with ID: {}", id);
        return patientHealthDataRepository.findById(id);
    }

    @Override
    public List<PatientHealthData> getAllMedicalRecords() {
        logger.info("Retrieving all medical records");
        return patientHealthDataRepository.findAll();
    }

    @Override
    public PatientHealthData updateMedicalRecord(int id, PatientHealthData record) {
        logger.info("Updating medical record with ID: {}, new data: {}", id, record);
        record.setId(id);
        return patientHealthDataRepository.save(record);
    }
}