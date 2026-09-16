package com.diaghealthy_notification.application.usecases;

import com.diaghealthy_notification.application.gateways.UserResponse;
import com.diaghealthy_notification.application.gateways.UserServiceGateway;
import com.diaghealthy_notification.application.inputs.NotificationCreateInput;
import com.diaghealthy_notification.domain.entities.Notification;
import com.diaghealthy_notification.domain.repositories.NotificationRepository;

import java.time.format.DateTimeFormatter;

public class CreateNotificationUseCase {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final NotificationRepository notificationRepository;
    private final UserServiceGateway userServiceGateway;

    public CreateNotificationUseCase(NotificationRepository notificationRepository, UserServiceGateway userServiceGateway) {
        this.notificationRepository = notificationRepository;
        this.userServiceGateway = userServiceGateway;
    }

    public Notification execute(NotificationCreateInput input) {

        String patientEmail = resolvePatientEmail(input);

        String message = buildMessage(input);

        Notification notification = new Notification(
                input.appointmentId(),
                input.patientId(),
                message
        );

        notification = notificationRepository.saveNotification(notification);

        sendReminder(notification, patientEmail);

        return notification;
    }

    private String resolvePatientEmail(NotificationCreateInput input) {

        if (input.patientEmail() != null && !input.patientEmail().isBlank()) {
            return input.patientEmail();
        }

        UserResponse patient = userServiceGateway.findPatientById(input.patientId());

        return patient.email();
    }

    private void sendReminder(Notification notification, String patientEmail) {
        try {
            System.out.println(
                    "Enviando lembrete por e-mail para " + patientEmail +
                            ": " + notification.getMessage()
            );

            notification.markAsSent();
        } catch (Exception exception) {
            notification.markAsFailed();
        }

        notificationRepository.updateNotification(notification);
    }

    private String buildMessage(NotificationCreateInput input) {
        return "Lembrete: você tem uma consulta agendada para " +
                input.scheduledAt().format(DATE_FORMAT) +
                ". Motivo: " + input.reason();
    }
}
