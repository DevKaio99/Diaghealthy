package com.diaghealthy_scheduling.infrastructure.config;

import com.diaghealthy_scheduling.application.gateways.UserServiceGateway;
import com.diaghealthy_scheduling.application.usecases.CreateAppointmentUseCase;
import com.diaghealthy_scheduling.application.usecases.UpdateAppointmentUseCase;
import com.diaghealthy_scheduling.domain.repositories.AppointmentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {
    @Bean
    public CreateAppointmentUseCase createAppointmentUseCase(AppointmentRepository appointmentRepository, UserServiceGateway userServiceGateway) {
        return new CreateAppointmentUseCase(appointmentRepository, userServiceGateway);
    }

    @Bean
    public UpdateAppointmentUseCase updateAppointmentUseCase(AppointmentRepository appointmentRepository) {
        return new UpdateAppointmentUseCase(appointmentRepository
        );
    }
}
