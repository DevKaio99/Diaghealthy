package com.diaghealthy_scheduling.domain.entities;

import com.diaghealthy_scheduling.domain.enuns.AppointmentStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class Appointment {

    private UUID id;
    private UUID patientId;
    private UUID doctorId;
    private UUID nurseId;
    private LocalDateTime scheduledAt;
    private AppointmentStatus status;
    private String reason;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Appointment(UUID patientId, UUID doctorId, UUID nurseId, LocalDateTime scheduledAt, String reason) {
        this.id = UUID.randomUUID();
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.nurseId = nurseId;
        this.scheduledAt = scheduledAt;
        this.status = AppointmentStatus.SCHEDULED;
        this.reason = reason;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();


    if (patientId == null) {
            throw new IllegalArgumentException("Informe um paciente");
        }

    if (doctorId == null) {
            throw new IllegalArgumentException("Informe um doutor");
        }

    if(reason == null || reason.isBlank()) {
            throw new IllegalArgumentException("Informe o motivo do agendamento");
        }

    }

    public static Appointment reconstitute(
            UUID id,
            UUID patientId,
            UUID doctorId,
            UUID nurseId,
            LocalDateTime scheduledAt,
            AppointmentStatus status,
            String reason,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        Appointment appointment = new Appointment(
                patientId,
                doctorId,
                nurseId,
                scheduledAt,
                reason
        );

        appointment.id = id;
        appointment.status = status;
        appointment.createdAt = createdAt;
        appointment.updatedAt = updatedAt;

        return appointment;
    }

    public UUID getId() {
        return id;
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

    public void setScheduledAt(LocalDateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
