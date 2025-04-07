package com.example.smarthealthmanagementsystem.entity;
import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "patient_visits")
public class PatientVisit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int patientId;
    private int doctorId;
    private Date visitDate;
    private String diagnosis;
    private String medication;
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }
    public int getDoctorId() { return doctorId; }
    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }
    public Date getVisitDate() { return visitDate; }
    public void setVisitDate(Date visitDate) { this.visitDate = visitDate; }
    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }
    public String getMedication() { return medication; }
    public void setMedication(String medication) { this.medication = medication; }
}