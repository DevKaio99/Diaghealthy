package com.fiap.diaghealthy.infrastructure.dtos.users.nurse;

import com.fiap.diaghealthy.domain.enuns.Role;

import java.time.LocalDateTime;
import java.util.UUID;

public record NurseResponseDTO (
        UUID id,
        String name,
        String email,
        String coren,
        LocalDateTime dateLastUpdate,
        LocalDateTime createdAt,
        boolean isActive,
        Role role
) {
}
