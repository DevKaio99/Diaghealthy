package com.fiap.diaghealthy.application.services;

import com.fiap.diaghealthy.domain.enuns.Role;

import java.util.UUID;

public interface CurrentUser {
        UUID getId();
        Role getRole();
    }

