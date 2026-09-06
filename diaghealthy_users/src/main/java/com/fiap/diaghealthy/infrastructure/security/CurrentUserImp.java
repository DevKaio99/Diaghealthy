package com.fiap.diaghealthy.infrastructure.security;

import com.fiap.diaghealthy.application.services.CurrentUser;
import com.fiap.diaghealthy.domain.entities.User;
import com.fiap.diaghealthy.domain.enuns.Role;
import com.fiap.diaghealthy.domain.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CurrentUserImp implements CurrentUser {

    private final UserRepository userRepository;

    public CurrentUserImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UUID getId() {
        return getUser().getId();
    }

    @Override
    public Role getRole() {
        return getUser().getRole();
    }

    private User getUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() ->
                        new IllegalStateException("Usuário autenticado não encontrado"));
    }
}