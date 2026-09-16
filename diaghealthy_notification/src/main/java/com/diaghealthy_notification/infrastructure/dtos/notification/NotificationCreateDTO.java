package com.diaghealthy_notification.infrastructure.dtos.notification;

import java.time.LocalDateTime;
import java.util.UUID;

public record NotificationCreateDTO(
        UUID appointmentId,
        UUID patientId,
        LocalDateTime scheduledAt,
        String reason,
        String patientEmail
) {
}
