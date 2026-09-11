package com.diaghealthy_notification.application.services;

import com.diaghealthy_notification.domain.enuns.Role;

import java.util.UUID;

public interface CurrentUser {
    UUID getId();
    Role getRole();
}
