package com.diaghealthy_scheduling.infrastructure.clients;

import com.diaghealthy_scheduling.domain.enuns.Role;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String name,
        String email,
        LocalDateTime dateLastUpdate,
        LocalDateTime createdAt,
        boolean active,
        Role role
) {
}