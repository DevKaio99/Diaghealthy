package com.diaghealthy_notification.infrastructure.security;

import com.diaghealthy_notification.application.services.CurrentUser;
import com.diaghealthy_notification.domain.enuns.Role;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CurrentUserImp implements CurrentUser {

    @Override
    public UUID getId() {
        return getAuthenticatedUser().id();
    }

    @Override
    public Role getRole() {
        return getAuthenticatedUser().role();
    }

    private AuthenticatedUser getAuthenticatedUser() {
        return (AuthenticatedUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
