package com.fiap.diaghealthy.application.inputs;

public record UserUpdatePassInput(
        String oldPassword,
        String newPassword
) {
}
