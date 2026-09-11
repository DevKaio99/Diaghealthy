package com.fiap.diaghealthy.application.usecases.user;

import com.fiap.diaghealthy.application.exceptions.ResourceNotFoundException;
import com.fiap.diaghealthy.domain.entities.User;
import com.fiap.diaghealthy.domain.repositories.UserRepository;

import java.util.UUID;

public class FindUserByIdUseCase {

    private final UserRepository userRepository;

    public FindUserByIdUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(UUID id) {
        return userRepository.findUserById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuário não encontrado")
                );
    }
}