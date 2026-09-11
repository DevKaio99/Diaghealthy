package com.diaghealthy_history.infrastructure.config;

import com.diaghealthy_history.application.gateways.UserServiceGateway;
import com.diaghealthy_history.application.usecases.FindHistoryByIdUseCase;
import com.diaghealthy_history.application.usecases.FindHistoryByPatientUseCase;
import com.diaghealthy_history.application.usecases.RegisterHistoryUseCase;
import com.diaghealthy_history.application.usecases.UpdateHistoryUseCase;
import com.diaghealthy_history.domain.repositories.MedicalRecordRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public RegisterHistoryUseCase registerHistoryUseCase(MedicalRecordRepository medicalRecordRepository, UserServiceGateway userServiceGateway) {
        return new RegisterHistoryUseCase(medicalRecordRepository, userServiceGateway);
    }

    @Bean
    public UpdateHistoryUseCase updateHistoryUseCase(MedicalRecordRepository medicalRecordRepository) {
        return new UpdateHistoryUseCase(medicalRecordRepository);
    }

    @Bean
    public FindHistoryByIdUseCase findHistoryByIdUseCase(MedicalRecordRepository medicalRecordRepository) {
        return new FindHistoryByIdUseCase(medicalRecordRepository);
    }

    @Bean
    public FindHistoryByPatientUseCase findHistoryByPatientUseCase(MedicalRecordRepository medicalRecordRepository) {
        return new FindHistoryByPatientUseCase(medicalRecordRepository);
    }
}
