package com.fiap.diaghealthy.application.inputs;

import java.time.LocalDate;

public record PatientUpdateInput(
        String name,
        String email,
        LocalDate dateOfBirth,
        boolean isActive
) {
}
