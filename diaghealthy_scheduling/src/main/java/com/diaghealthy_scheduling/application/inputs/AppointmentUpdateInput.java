package com.diaghealthy_scheduling.application.inputs;

import com.diaghealthy_scheduling.domain.enuns.AppointmentStatus;

import java.time.LocalDateTime;

public record AppointmentUpdateInput(
        LocalDateTime scheduledAt,
        AppointmentStatus status,
        String reason
) {
}
