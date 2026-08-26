package com.fiap.diaghealthy.infrastructure.dtos.user.doctor;

import com.fiap.diaghealthy.domain.enuns.Role;

import java.time.LocalDateTime;
import java.util.UUID;

public record DoctorResponseDTO(
        UUID id,
        String name,
        String email,
        LocalDateTime dateLastUpdate,
        LocalDateTime createdAt,
        String crm,
        String speciality,
        boolean isActive,
        Role role
) {
}
