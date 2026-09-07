package com.diaghealthy_scheduling.infrastructure.dtos.appointment;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentCreateDTO(
        UUID patientId,
        UUID doctorId,
        UUID nurseId,
        LocalDateTime scheduledAt,
        String reason
){
}
