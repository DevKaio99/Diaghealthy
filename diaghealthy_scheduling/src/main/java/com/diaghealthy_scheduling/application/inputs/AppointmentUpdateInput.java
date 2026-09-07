package com.diaghealthy_scheduling.application.inputs;

import com.diaghealthy_scheduling.domain.enuns.AppointmentStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentUpdateInput(
        UUID id,
        LocalDateTime scheduledAt,
        AppointmentStatus status,
        String reason
) {
}
