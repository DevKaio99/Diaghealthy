package com.fiap.diaghealthy.infrastructure.dtos.users.user;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String name,
        String email,
        LocalDateTime dateLastUpdate
) {
}
