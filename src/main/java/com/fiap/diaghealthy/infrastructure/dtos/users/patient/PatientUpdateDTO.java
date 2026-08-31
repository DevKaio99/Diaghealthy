package com.fiap.diaghealthy.infrastructure.dtos.users.patient;

import java.time.LocalDate;

public record PatientUpdateDTO (
        String name,
        String email,
        LocalDate dateOfBirth,
        boolean isActive
){
}
