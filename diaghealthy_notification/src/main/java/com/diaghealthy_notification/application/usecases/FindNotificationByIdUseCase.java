package com.diaghealthy_notification.application.usecases;

import com.diaghealthy_notification.application.exceptions.ResourceNotFoundException;
import com.diaghealthy_notification.domain.entities.Notification;
import com.diaghealthy_notification.domain.repositories.NotificationRepository;

import java.util.UUID;

public class FindNotificationByIdUseCase {

    private final NotificationRepository notificationRepository;

    public FindNotificationByIdUseCase(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public Notification execute(UUID id) {
        return notificationRepository.findNotificationById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notificação não encontrada"));
    }
}
