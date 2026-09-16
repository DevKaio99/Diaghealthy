package com.diaghealthy_scheduling.application.gateways;

import com.diaghealthy_scheduling.domain.enuns.Role;

import java.util.UUID;

public record UserResponse(
        UUID id,
        String email,
        Role role,
        boolean active
) {
}