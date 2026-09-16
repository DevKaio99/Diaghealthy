package com.diaghealthy_scheduling.infrastructure.dtos.appointment;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentEvent(
        UUID appointmentId,
        UUID patientId,
        UUID doctorId,
        UUID nurseId,
        LocalDateTime scheduledAt,
        String status,
        String reason,
        String patientEmail
) {
}
