package com.fiap.diaghealthy.infrastructure.dtos.users.patient;

import com.fiap.diaghealthy.domain.enuns.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record PatientResponseDTO(
        UUID id,
        String name,
        String email,
        LocalDate dateOfBirth,
        LocalDateTime dateLastUpdate,
        LocalDateTime createdAt,
        boolean isActive,
        Role role
) {
}
