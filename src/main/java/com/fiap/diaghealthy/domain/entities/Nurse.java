package com.fiap.diaghealthy.domain.entities;

import com.fiap.diaghealthy.domain.enuns.Role;

import java.time.LocalDateTime;
import java.util.UUID;

public class Nurse extends User {
    protected String coren;

    public Nurse(String name, String email, String password, String coren) {
        super(name, email, password, Role.NURSE);
        this.coren = coren;

        if (coren == null || coren.isBlank()) {
            throw new IllegalArgumentException("Insira o COREN");
        }
    }

    public static Nurse reconstitute(
            UUID id,
            String name,
            String email,
            String password,
            LocalDateTime dateLastUpdate,
            LocalDateTime createdAt,
            boolean isActive,
            String coren
    ) {
        Nurse nurse = new Nurse(
                name,
                email,
                password,
                coren
        );

        nurse.id = id;
        nurse.dateLastUpdate = dateLastUpdate;
        nurse.createdAt = createdAt;
        nurse.isActive = isActive;

        return nurse;
    }

    public String getCoren() {
        return coren;
    }

    public void setCoren(String coren) {
        this.coren = coren;
    }
}
