package com.fiap.diaghealthy.application.inputs;

public record DoctorUpdateInput(
        String name,
        String email,
        boolean isActive,
        String crm,
        String speciality
) {
}
