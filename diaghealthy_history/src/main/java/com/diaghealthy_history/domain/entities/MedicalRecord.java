package com.diaghealthy_history.domain.entities;

import com.diaghealthy_history.domain.enuns.HistoryStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class MedicalRecord {

    private UUID id;
    private UUID appointmentId;
    private UUID patientId;
    private UUID doctorId;
    private UUID nurseId;
    private LocalDateTime scheduledAt;
    private HistoryStatus status;
    private String reason;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public MedicalRecord(
            UUID appointmentId,
            UUID patientId,
            UUID doctorId,
            UUID nurseId,
            LocalDateTime scheduledAt,
            HistoryStatus status,
            String reason
    ) {
        this.id = UUID.randomUUID();
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.nurseId = nurseId;
        this.scheduledAt = scheduledAt;
        this.status = status;
        this.reason = reason;
        this.notes = null;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

        if (appointmentId == null) {
            throw new IllegalArgumentException("Informe o agendamento relacionado");
        }

        if (patientId == null) {
            throw new IllegalArgumentException("Informe um paciente");
        }

        if (doctorId == null) {
            throw new IllegalArgumentException("Informe um doutor");
        }

        if (reason == null || reason.isBlank()) {
            throw new IllegalArgumentException("Informe o motivo da consulta");
        }
    }

    public static MedicalRecord reconstitute(
            UUID id,
            UUID appointmentId,
            UUID patientId,
            UUID doctorId,
            UUID nurseId,
            LocalDateTime scheduledAt,
            HistoryStatus status,
            String reason,
            String notes,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        MedicalRecord record = new MedicalRecord(
                appointmentId,
                patientId,
                doctorId,
                nurseId,
                scheduledAt,
                status,
                reason
        );

        record.id = id;
        record.notes = notes;
        record.createdAt = createdAt;
        record.updatedAt = updatedAt;

        return record;
    }

    public UUID getId() {
        return id;
    }

    public UUID getAppointmentId() {
        return appointmentId;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public UUID getDoctorId() {
        return doctorId;
    }

    public UUID getNurseId() {
        return nurseId;
    }

    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public HistoryStatus getStatus() {
        return status;
    }

    public void setStatus(HistoryStatus status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
