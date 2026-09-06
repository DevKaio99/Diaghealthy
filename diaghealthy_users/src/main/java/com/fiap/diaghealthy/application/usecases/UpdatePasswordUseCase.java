package com.fiap.diaghealthy.application.usecases;

import com.fiap.diaghealthy.application.exceptions.BusinessException;
import com.fiap.diaghealthy.application.exceptions.ResourceNotFoundException;
import com.fiap.diaghealthy.application.exceptions.UnauthorizedException;
import com.fiap.diaghealthy.application.inputs.UserUpdatePassInput;
import com.fiap.diaghealthy.application.services.CurrentUser;
import com.fiap.diaghealthy.domain.entities.User;
import com.fiap.diaghealthy.domain.enuns.Role;
import com.fiap.diaghealthy.domain.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.UUID;

public class UpdatePasswordUseCase {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CurrentUser currentUser;


    public UpdatePasswordUseCase(UserRepository userRepository, PasswordEncoder passwordEncoder, CurrentUser currentUser) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.currentUser = currentUser;
    }

    public User execute (UUID id, UserUpdatePassInput dto) throws UnauthorizedException {
        User user = userRepository.findUserById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        UUID loggedUserId = currentUser.getId();
        Role loggedUserRole = currentUser.getRole();

        if (!loggedUserId.equals(id) && loggedUserRole != Role.ADMIN) {
            throw new UnauthorizedException("Usuário não possui permissão para alterar este usuário");
        }

        if (!passwordEncoder.matches(dto.oldPassword(), user.getPassword())) {
            throw new BusinessException("Senha atual incorreta");
        }
        user.setPassword(passwordEncoder.encode(dto.newPassword()));
        user.setDateLastUpdate(LocalDateTime.now());

        return userRepository.updateUserPass(user);

    }

    }
