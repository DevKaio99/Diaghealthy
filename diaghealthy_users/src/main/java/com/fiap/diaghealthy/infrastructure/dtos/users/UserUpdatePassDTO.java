package com.fiap.diaghealthy.infrastructure.dtos.users;

import jakarta.validation.constraints.NotBlank;

public record UserUpdatePassDTO(
        String oldPassword,
        @NotBlank(message = "Insira uma nova senha válida")
        String newPassword
) {
}
