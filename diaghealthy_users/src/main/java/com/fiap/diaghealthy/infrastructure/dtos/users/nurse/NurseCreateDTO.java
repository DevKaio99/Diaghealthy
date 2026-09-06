package com.fiap.diaghealthy.infrastructure.dtos.users.nurse;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record NurseCreateDTO (
        @NotBlank(message = "O campo Nome não pode estar vazio")
        String name,
        @Email(message = "O E-mail inserido é inválido")
        String email,
        @NotBlank(message = "O campo Senha não pode estar vazio")
        String password,
        @NotBlank(message = "Insira o CRM")
        String coren
) {
}
