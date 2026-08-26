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

            String email = "admin@email.com";

            if (userRepository.findByEmailIgnoreCase(email).isEmpty()) {

                User admin = new User(
                        "Administrador",
                        email,
                        passwordEncoder.encode("admin123"),
                        Role.ADMIN
                );

                userRepository.saveUser(admin);
            }
        };
    }
}
