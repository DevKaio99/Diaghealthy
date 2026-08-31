package com.fiap.diaghealthy.infrastructure.config;

import com.fiap.diaghealthy.application.services.CurrentUser;
import com.fiap.diaghealthy.application.usecases.*;
import com.fiap.diaghealthy.domain.repositories.DoctorRepository;
import com.fiap.diaghealthy.domain.repositories.NurseRepository;
import com.fiap.diaghealthy.domain.repositories.PatientRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

    @Bean
    public CreatePatientUseCase createPatientUseCase (PatientRepository patientRepository, PasswordEncoder passwordEncoder) {
        return new CreatePatientUseCase(patientRepository, passwordEncoder);
    }

    @Bean
    public UpdatePatientUseCase updatePatientUseCase (PatientRepository patientRepository, CurrentUser currentUser) {
        return new UpdatePatientUseCase(patientRepository, currentUser);
    }

    @Bean
    public CreateNurseUseCase createNurseUseCase (NurseRepository nurseRepository, PasswordEncoder passwordEncoder) {
        return new CreateNurseUseCase(nurseRepository, passwordEncoder);
    }

    @Bean
    public UpdateNurseUseCase updateNurseUseCase (NurseRepository nurseRepository, CurrentUser currentUser) {
        return new UpdateNurseUseCase(nurseRepository, currentUser);
    }

}

