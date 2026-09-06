package com.fiap.diaghealthy.application.inputs;

public record NurseCreateInput(
        String name,
        String email,
        String password,
        String coren
) {
}
