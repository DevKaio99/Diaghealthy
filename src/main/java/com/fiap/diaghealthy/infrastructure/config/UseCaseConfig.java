package com.fiap.diaghealthy.infrastructure.config;

import com.fiap.diaghealthy.application.services.CurrentUser;
import com.fiap.diaghealthy.application.usecases.CreateDoctorUseCase;
import com.fiap.diaghealthy.application.usecases.UpdateDoctorUseCase;
import com.fiap.diaghealthy.domain.repositories.DoctorRepository;
import com.fiap.diaghealthy.infrastructure.mappers.DoctorMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UseCaseConfig {
    @Bean
    public CreateDoctorUseCase createDoctorUseCase (DoctorRepository doctorRepository, PasswordEncoder passwordEncoder) {
        return new CreateDoctorUseCase(doctorRepository, passwordEncoder);
    }

    @Bean
    public UpdateDoctorUseCase updateDoctorUseCase (DoctorRepository doctorRepository, CurrentUser currentUser) {
        return new UpdateDoctorUseCase(doctorRepository, currentUser);
    }

}

