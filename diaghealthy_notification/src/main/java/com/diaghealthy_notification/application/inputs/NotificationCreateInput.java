package com.diaghealthy_notification.application.inputs;

import java.time.LocalDateTime;
import java.util.UUID;

public record NotificationCreateInput(
        UUID appointmentId,
        UUID patientId,
        LocalDateTime scheduledAt,
        String reason
) {
}
