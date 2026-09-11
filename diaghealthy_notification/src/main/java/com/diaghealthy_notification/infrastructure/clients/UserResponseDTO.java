package com.diaghealthy_notification.infrastructure.clients;

import com.diaghealthy_notification.domain.enuns.Role;

import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String name,
        String email,
        boolean active,
        Role role
) {
}
