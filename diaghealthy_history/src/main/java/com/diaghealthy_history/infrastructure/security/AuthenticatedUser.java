package com.diaghealthy_history.infrastructure.security;

import com.diaghealthy_history.domain.enuns.Role;

import java.util.UUID;

public record AuthenticatedUser(UUID id, String email, Role role) {
}
