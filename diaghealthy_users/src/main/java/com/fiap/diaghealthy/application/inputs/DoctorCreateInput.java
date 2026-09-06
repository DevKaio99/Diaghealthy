package com.fiap.diaghealthy.application.inputs;

public record DoctorCreateInput (
        String name,
        String email,
        String password,
        String crm,
        String speciality
){
}
