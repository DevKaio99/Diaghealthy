package com.diaghealthy_notification.infrastructure.dtos.notification;

import com.diaghealthy_notification.domain.enuns.NotificationStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record NotificationResponseDTO(
        UUID id,
        UUID appointmentId,
        UUID patientId,
        String message,
        NotificationStatus status,
        LocalDateTime createdAt,
        LocalDateTime sentAt
) {
}
