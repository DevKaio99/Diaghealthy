package com.diaghealthy_scheduling.infrastructure.dtos.appointment;

import com.diaghealthy_scheduling.domain.enuns.AppointmentStatus;

import java.time.LocalDateTime;

public record AppointmentUpdateDTO(
        LocalDateTime scheduledAt,
        AppointmentStatus status,
        String reason
) {
}
