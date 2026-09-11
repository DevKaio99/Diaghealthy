package com.diaghealthy_history.application.gateways;

import com.diaghealthy_history.domain.enuns.Role;

import java.util.UUID;

public record UserResponse(
        UUID id,
        Role role,
        boolean active
) {
}
