package com.fiap.diaghealthy.infrastructure.dtos.users.doctor;

public record DoctorUpdateDTO(
        String name,
        String email,
        boolean isActive,
        String crm,
        String speciality
) {
}
