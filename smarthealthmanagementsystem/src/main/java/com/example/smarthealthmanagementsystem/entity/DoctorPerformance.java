package com.example.smarthealthmanagementsystem.entity;
import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "doctor_performance")
public class DoctorPerformance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int doctorId;
    private Date visitDate;
    private int patientsSeen;
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getDoctorId() { return doctorId; }
    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }
    public Date getVisitDate() { return visitDate; }
    public void setVisitDate(Date visitDate) { this.visitDate = visitDate; }
    public int getPatientsSeen() { return patientsSeen; }
    public void setPatientsSeen(int patientsSeen) { this.patientsSeen = patientsSeen; }
}