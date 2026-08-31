package com.fiap.diaghealthy.application.inputs;

import java.time.LocalDate;

public record PatientCreateInput(
        String name,
        String email,
        String password,
        LocalDate dateOfBirth
) {
}
