package com.diaghealthy_notification.infrastructure.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String APPOINTMENT_EXCHANGE =
            "appointment.exchange";

    public static final String NOTIFICATION_QUEUE =
            "notification.queue";

    public static final String APPOINTMENT_CREATED_ROUTING_KEY =
            "appointment.created";

    @Bean
    public DirectExchange appointmentExchange() {
        return new DirectExchange(APPOINTMENT_EXCHANGE);
    }

    @Bean
    public Queue notificationQueue() {
        return new Queue(NOTIFICATION_QUEUE, true);
    }

    @Bean
    public Binding notificationBinding(
            Queue notificationQueue,
            DirectExchange appointmentExchange
    ) {
        return BindingBuilder
                .bind(notificationQueue)
                .to(appointmentExchange)
                .with(APPOINTMENT_CREATED_ROUTING_KEY);
    }
}