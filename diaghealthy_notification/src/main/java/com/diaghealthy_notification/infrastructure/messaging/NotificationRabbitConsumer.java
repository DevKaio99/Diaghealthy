package com.diaghealthy_notification.infrastructure.messaging;

import com.diaghealthy_notification.application.inputs.NotificationCreateInput;
import com.diaghealthy_notification.application.usecases.CreateNotificationUseCase;
import com.diaghealthy_notification.infrastructure.dtos.notification.NotificationCreateDTO;
import com.diaghealthy_notification.infrastructure.mappers.NotificationMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
public class NotificationRabbitConsumer {

    private final CreateNotificationUseCase createNotificationUseCase;
    private final NotificationMapper notificationMapper;

    public NotificationRabbitConsumer(
            CreateNotificationUseCase createNotificationUseCase,
            NotificationMapper notificationMapper
    ) {
        this.createNotificationUseCase = createNotificationUseCase;
        this.notificationMapper = notificationMapper;
    }

    @RabbitListener(queues = "notification.queue")
    public void consume(NotificationCreateDTO dto) {

        NotificationCreateInput input = notificationMapper.toCreateInput(dto);

        createNotificationUseCase.execute(input);
    }
}