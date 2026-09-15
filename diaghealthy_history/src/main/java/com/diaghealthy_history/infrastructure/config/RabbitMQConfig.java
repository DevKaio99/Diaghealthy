package com.diaghealthy_history.infrastructure.config;

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

    public static final String HISTORY_QUEUE =
            "history.queue";

    public static final String APPOINTMENT_CREATED_ROUTING_KEY =
            "appointment.created";

    @Bean
    public DirectExchange appointmentExchange() {
        return new DirectExchange(APPOINTMENT_EXCHANGE);
    }

    @Bean
    public Queue historyQueue() {
        return new Queue(HISTORY_QUEUE, true);
    }

    @Bean
    public Binding historyBinding(
            Queue historyQueue,
            DirectExchange appointmentExchange
    ) {
        return BindingBuilder
                .bind(historyQueue)
                .to(appointmentExchange)
                .with(APPOINTMENT_CREATED_ROUTING_KEY);
    }
}