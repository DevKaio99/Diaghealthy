package com.fiap.diaghealthy.domain.repositories;

import com.fiap.diaghealthy.domain.entities.Doctor;
import com.fiap.diaghealthy.domain.entities.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<User> findByEmailIgnoreCase(String email);
    User saveUser (User user);
    Optional <User> findUserById (UUID id);
    User updateUserPass (User user);
}
