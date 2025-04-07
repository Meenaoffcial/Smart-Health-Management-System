package com.example.smarthealthmanagementsystem.entity;



import jakarta.persistence.*;

@Entity
@Table(name = "medication_statistics")
public class MedicationStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String medication;
    private int totalPrescriptions;
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getMedication() { return medication; }
    public void setMedication(String medication) { this.medication = medication; }
    public int getTotalPrescriptions() { return totalPrescriptions; }
    public void setTotalPrescriptions(int totalPrescriptions) { this.totalPrescriptions = totalPrescriptions; }
}