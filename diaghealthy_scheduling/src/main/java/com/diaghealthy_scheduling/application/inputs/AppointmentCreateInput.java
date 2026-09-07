package com.diaghealthy_scheduling.application.inputs;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentCreateInput(
        UUID patientId,
        UUID doctorId,
        UUID nurseId,
        LocalDateTime scheduledAt,
        String reason
) {
}