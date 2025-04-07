package com.example.smarthealthmanagementsystem.entity;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "patient_health_data")
public class PatientHealthData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int patientId;
    private Date recordDate;
    private double weight;
    private String bloodPressure;
    private int heartRate;
    private String notes;

    // Getters and setters...
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }
    public Date getRecordDate() { return recordDate; }
    public void setRecordDate(Date recordDate) { this.recordDate = recordDate; }
    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }
    public String getBloodPressure() { return bloodPressure; }
    public void setBloodPressure(String bloodPressure) { this.bloodPressure = bloodPressure; }
    public int getHeartRate() { return heartRate; }
    public void setHeartRate(int heartRate) { this.heartRate = heartRate; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}