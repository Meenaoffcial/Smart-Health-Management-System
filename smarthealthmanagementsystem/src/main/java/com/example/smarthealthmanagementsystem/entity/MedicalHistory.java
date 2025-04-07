package com.example.smarthealthmanagementsystem.entity;

import jakarta.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "medical_history")
public class MedicalHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "patient_id", nullable = false)
    private Integer patientId;

    @Column(name = "history_date", nullable = false)
    private Date historyDate;

    @Column(name = "condition_description", columnDefinition = "TEXT")
    private String conditionDescription;

    @Column(name = "treatment_description", columnDefinition = "TEXT")
    private String treatmentDescription;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    // Constructors, Getters, and Setters...
    public MedicalHistory() {}

    public MedicalHistory(Integer patientId, Date historyDate, String conditionDescription, String treatmentDescription, String notes, Timestamp createdAt, Timestamp updatedAt) {
        this.patientId = patientId;
        this.historyDate = historyDate;
        this.conditionDescription = conditionDescription;
        this.treatmentDescription = treatmentDescription;
        this.notes = notes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters...
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getPatientId() { return patientId; }
    public void setPatientId(Integer patientId) { this.patientId = patientId; }

    public Date getHistoryDate() { return historyDate; }
    public void setHistoryDate(Date historyDate) { this.historyDate = historyDate; }

    public String getConditionDescription() { return conditionDescription; }
    public void setConditionDescription(String conditionDescription) { this.conditionDescription = conditionDescription; }

    public String getTreatmentDescription() { return treatmentDescription; }
    public void setTreatmentDescription(String treatmentDescription) { this.treatmentDescription = treatmentDescription; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
}
