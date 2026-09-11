package com.diaghealthy_history.infrastructure.dtos.history;

import com.diaghealthy_history.domain.enuns.HistoryStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record HistoryResponseDTO(
        UUID id,
        UUID appointmentId,
        UUID patientId,
        UUID doctorId,
        UUID nurseId,
        LocalDateTime scheduledAt,
        HistoryStatus status,
        String reason,
        String notes,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
