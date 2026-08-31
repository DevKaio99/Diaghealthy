package com.fiap.diaghealthy.domain.entities;

import com.fiap.diaghealthy.domain.enuns.Role;

import java.time.LocalDateTime;
import java.util.UUID;

public class Doctor extends User {

    protected String CRM;
    protected String speciality;


    public Doctor(String name, String email, String password, String CRM, String speciality) {
        super(name, email, password, Role.DOCTOR);
        this.CRM = CRM;
        this.speciality = speciality;

        if (CRM == null || CRM.isBlank()) {
            throw new IllegalArgumentException("Insira um CRM");
        }
    }

    public static Doctor reconstitute(
            UUID id,
            String name,
            String email,
            String password,
            LocalDateTime dateLastUpdate,
            LocalDateTime createdAt,
            boolean isActive,
            String CRM,
            String speciality
    ) {
        Doctor doctor = new Doctor(
                name,
                email,
                password,
                CRM,
                speciality
        );

        doctor.id = id;
        doctor.dateLastUpdate = dateLastUpdate;
        doctor.createdAt = createdAt;
        doctor.isActive = isActive;

        return doctor;
    }

    public String getCRM() {
        return CRM;
    }

    public void setCRM(String CRM) {
        this.CRM = CRM;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
}
