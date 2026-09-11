package com.diaghealthy_notification.infrastructure.config;

import com.diaghealthy_notification.application.gateways.UserServiceGateway;
import com.diaghealthy_notification.application.usecases.CreateNotificationUseCase;
import com.diaghealthy_notification.application.usecases.FindNotificationByIdUseCase;
import com.diaghealthy_notification.application.usecases.FindNotificationsByPatientUseCase;
import com.diaghealthy_notification.domain.repositories.NotificationRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public CreateNotificationUseCase createNotificationUseCase(NotificationRepository notificationRepository, UserServiceGateway userServiceGateway) {
        return new CreateNotificationUseCase(notificationRepository, userServiceGateway);
    }

    @Bean
    public FindNotificationByIdUseCase findNotificationByIdUseCase(NotificationRepository notificationRepository) {
        return new FindNotificationByIdUseCase(notificationRepository);
    }

    @Bean
    public FindNotificationsByPatientUseCase findNotificationsByPatientUseCase(NotificationRepository notificationRepository) {
        return new FindNotificationsByPatientUseCase(notificationRepository);
    }
}
