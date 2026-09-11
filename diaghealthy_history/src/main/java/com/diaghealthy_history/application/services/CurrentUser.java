package com.diaghealthy_history.application.services;

import com.diaghealthy_history.domain.enuns.Role;

import java.util.UUID;

public interface CurrentUser {
    UUID getId();
    Role getRole();
}
