package com.fiap.diaghealthy.infrastructure.dtos.users.user;

import com.fiap.diaghealthy.domain.enuns.Role;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String name,
        String email,
        boolean active,
        LocalDateTime dateLastUpdate,
        Role role
) {
}
