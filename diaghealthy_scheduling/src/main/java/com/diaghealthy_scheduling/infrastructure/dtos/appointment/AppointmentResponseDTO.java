package com.diaghealthy_scheduling.infrastructure.dtos.appointment;

import com.diaghealthy_scheduling.domain.enuns.AppointmentStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentResponseDTO(
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
}
