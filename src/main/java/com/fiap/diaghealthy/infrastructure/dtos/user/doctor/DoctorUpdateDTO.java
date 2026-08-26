package com.fiap.diaghealthy.infrastructure.dtos.user.doctor;

public record DoctorUpdateDTO(
        String name,
        String email,
        boolean isActive,
        String crm,
        String specialit
) {
}
