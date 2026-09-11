package com.diaghealthy_notification.infrastructure.security;

import com.diaghealthy_notification.domain.enuns.Role;

import java.util.UUID;

public record AuthenticatedUser(UUID id, String email, Role role) {
}
