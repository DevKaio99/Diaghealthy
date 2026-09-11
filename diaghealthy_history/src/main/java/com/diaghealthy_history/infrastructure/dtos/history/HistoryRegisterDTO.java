package com.diaghealthy_history.infrastructure.dtos.history;

import com.diaghealthy_history.domain.enuns.HistoryStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record HistoryRegisterDTO(
        UUID appointmentId,
        UUID patientId,
        UUID doctorId,
        UUID nurseId,
        LocalDateTime scheduledAt,
        HistoryStatus status,
        String reason
) {
}
