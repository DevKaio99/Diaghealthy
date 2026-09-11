package com.diaghealthy_notification.infrastructure.mappers;

import com.diaghealthy_notification.application.inputs.NotificationCreateInput;
import com.diaghealthy_notification.domain.entities.Notification;
import com.diaghealthy_notification.infrastructure.dtos.notification.NotificationCreateDTO;
import com.diaghealthy_notification.infrastructure.dtos.notification.NotificationResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    public NotificationCreateInput toCreateInput(NotificationCreateDTO notificationCreateDTO) {
        return new NotificationCreateInput(
                notificationCreateDTO.appointmentId(),
                notificationCreateDTO.patientId(),
                notificationCreateDTO.scheduledAt(),
                notificationCreateDTO.reason()
        );
    }

    public NotificationResponseDTO toDto(Notification notification) {
        return new NotificationResponseDTO(
                notification.getId(),
                notification.getAppointmentId(),
                notification.getPatientId(),
                notification.getMessage(),
                notification.getStatus(),
                notification.getCreatedAt(),
                notification.getSentAt()
        );
    }
}
