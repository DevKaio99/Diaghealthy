package com.diaghealthy_scheduling.infrastructure.config;

import com.diaghealthy_scheduling.application.gateways.AppointmentEventGateway;
import com.diaghealthy_scheduling.application.gateways.UserServiceGateway;
import com.diaghealthy_scheduling.application.usecases.CreateAppointmentUseCase;
import com.diaghealthy_scheduling.application.usecases.UpdateAppointmentUseCase;
import com.diaghealthy_scheduling.domain.repositories.AppointmentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {
    @Bean
    public CreateAppointmentUseCase createAppointmentUseCase(AppointmentRepository appointmentRepository, UserServiceGateway userServiceGateway, AppointmentEventGateway appointmentEventGateway) {
        return new CreateAppointmentUseCase(appointmentRepository, userServiceGateway, appointmentEventGateway);
    }

    @Bean
    public UpdateAppointmentUseCase updateAppointmentUseCase(
            AppointmentRepository appointmentRepository,
            UserServiceGateway userServiceGateway,
            AppointmentEventGateway appointmentEventGateway
    ) {
        return new UpdateAppointmentUseCase(appointmentRepository, userServiceGateway, appointmentEventGateway);
    }
}
