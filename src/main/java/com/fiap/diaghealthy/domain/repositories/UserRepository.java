package com.fiap.diaghealthy.domain.repositories;

import com.fiap.diaghealthy.domain.entities.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmailIgnoreCase(String email);
    User saveUser (User user);
}
