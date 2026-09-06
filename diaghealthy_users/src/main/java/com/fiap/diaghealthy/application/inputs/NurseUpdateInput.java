package com.fiap.diaghealthy.application.inputs;

public record NurseUpdateInput(
        String name,
        String email,
        String coren,
        boolean isActive
) {
}
