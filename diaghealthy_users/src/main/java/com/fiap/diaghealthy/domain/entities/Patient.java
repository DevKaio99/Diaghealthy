package com.fiap.diaghealthy.domain.entities;

import com.fiap.diaghealthy.application.exceptions.BusinessException;
import com.fiap.diaghealthy.domain.enuns.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class Patient extends User{
    protected LocalDate dateOfBirth;

    public Patient (String name, String email, String password, LocalDate dateOfBirth) {
        super(name, email, password, Role.PATIENT);
        this.dateOfBirth = dateOfBirth;

        if (dateOfBirth == null) {
            throw new IllegalArgumentException("Insira a data de nascimento");
        }

        if(dateOfBirth.isAfter(LocalDate.now())) {
            throw new BusinessException("Data inválida");
        }
}

    public static Patient reconstitute(
            UUID id,
            String name,
            String email,
            String password,
            LocalDateTime dateLastUpdate,
            LocalDateTime createdAt,
            boolean isActive,
            LocalDate dateOfBirth
    ) {
        Patient patient = new Patient(
                name,
                email,
                password,
                dateOfBirth
        );

        patient.id = id;
        patient.dateLastUpdate = dateLastUpdate;
        patient.createdAt = createdAt;
        patient.isActive = isActive;

        return patient;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
