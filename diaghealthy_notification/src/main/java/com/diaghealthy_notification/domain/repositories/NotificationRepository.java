package com.diaghealthy_notification.domain.repositories;

import com.diaghealthy_notification.domain.entities.Notification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotificationRepository {
    Notification saveNotification(Notification notification);
    Notification updateNotification(Notification notification);
    Optional<Notification> findNotificationById(UUID id);
    List<Notification> findNotificationsByPatientId(UUID patientId);
}
