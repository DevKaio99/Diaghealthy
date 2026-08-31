package com.fiap.diaghealthy.infrastructure.config;

import com.fiap.diaghealthy.domain.entities.User;
import com.fiap.diaghealthy.domain.enuns.Role;
import com.fiap.diaghealthy.domain.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {

            createUser(
                    userRepository,
                    passwordEncoder,
                    "Administrador",
                    "admin@email.com",
                    "admin123",
                    Role.ADMIN
            );

            createUser(
                    userRepository,
                    passwordEncoder,
                    "Paciente Teste",
                    "patient@email.com",
                    "patient123",
                    Role.PATIENT
            );

            createUser(
                    userRepository,
                    passwordEncoder,
                    "Enfermeiro Teste",
                    "nurse@email.com",
                    "nurse123",
                    Role.NURSE
            );

            createUser(
                    userRepository,
                    passwordEncoder,
                    "Doutor Teste",
                    "doctor@email.com",
                    "doctor123",
                    Role.DOCTOR
            );
        };
    }

    private void createUser(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            String name,
            String email,
            String password,
            Role role
    ) {
        if (userRepository.findByEmailIgnoreCase(email).isEmpty()) {

            User user = new User(
                    name,
                    email,
                    passwordEncoder.encode(password),
                    role
            );

            userRepository.saveUser(user);
        }
    }
}