package com.diaghealthy_history.infrastructure.clients;

import com.diaghealthy_history.domain.enuns.Role;

import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String name,
        String email,
        boolean active,
        Role role
) {
}
