package com.fiap.diaghealthy.infrastructure.dtos.users.patient;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record PatientCreateDTO(
        @NotBlank(message = "O campo Nome não pode estar vazio")
        String name,
        @Email(message = "O E-mail inserido é inválido")
        String email,
        @NotBlank(message = "O campo Senha não pode estar vazio")
        String password,
        @NotNull(message = "Insira a data de nascimento")
        @Past(message = "Data de nascimento inválida")
        LocalDate dateOfBirth
) {
}
