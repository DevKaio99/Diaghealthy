package com.diaghealthy_notification.domain.entities;

import com.diaghealthy_notification.domain.enuns.NotificationStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class Notification {

    private UUID id;
    private UUID appointmentId;
    private UUID patientId;
    private String message;
    private NotificationStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime sentAt;

    public Notification(UUID appointmentId, UUID patientId, String message) {
        this.id = UUID.randomUUID();
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.message = message;
        this.status = NotificationStatus.PENDING;
        this.createdAt = LocalDateTime.now();
        this.sentAt = null;

        if (appointmentId == null) {
            throw new IllegalArgumentException("Informe o agendamento relacionado");
        }

        if (patientId == null) {
            throw new IllegalArgumentException("Informe o paciente");
        }

        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Informe a mensagem da notificação");
        }
    }

    public static Notification reconstitute(
            UUID id,
            UUID appointmentId,
            UUID patientId,
            String message,
            NotificationStatus status,
            LocalDateTime createdAt,
            LocalDateTime sentAt
    ) {
        Notification notification = new Notification(appointmentId, patientId, message);

        notification.id = id;
        notification.status = status;
        notification.createdAt = createdAt;
        notification.sentAt = sentAt;

        return notification;
    }

    public void markAsSent() {
        this.status = NotificationStatus.SENT;
        this.sentAt = LocalDateTime.now();
    }

    public void markAsFailed() {
        this.status = NotificationStatus.FAILED;
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

    public String getMessage() {
        return message;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }
}
