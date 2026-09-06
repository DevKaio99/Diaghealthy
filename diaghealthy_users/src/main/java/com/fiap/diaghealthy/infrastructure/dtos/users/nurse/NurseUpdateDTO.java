package com.fiap.diaghealthy.infrastructure.dtos.users.nurse;

public record NurseUpdateDTO (
        String name,
        String email,
        String coren,
        boolean isActive
) {
}
