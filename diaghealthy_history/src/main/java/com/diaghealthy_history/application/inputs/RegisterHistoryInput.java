package com.diaghealthy_history.application.inputs;

import com.diaghealthy_history.domain.enuns.HistoryStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record RegisterHistoryInput(
        UUID appointmentId,
        UUID patientId,
        UUID doctorId,
        UUID nurseId,
        LocalDateTime scheduledAt,
        HistoryStatus status,
        String reason
) {
}
