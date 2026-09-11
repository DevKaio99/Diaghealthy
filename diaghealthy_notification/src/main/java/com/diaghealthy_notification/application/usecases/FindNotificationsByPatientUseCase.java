package com.diaghealthy_notification.application.usecases;

import com.diaghealthy_notification.domain.entities.Notification;
import com.diaghealthy_notification.domain.repositories.NotificationRepository;

import java.util.List;
import java.util.UUID;

public class FindNotificationsByPatientUseCase {

    private final NotificationRepository notificationRepository;

    public FindNotificationsByPatientUseCase(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<Notification> execute(UUID patientId) {
        return notificationRepository.findNotificationsByPatientId(patientId);
    }
}
