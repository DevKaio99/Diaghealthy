package com.diaghealthy_scheduling.infrastructure.messaging;

import com.diaghealthy_scheduling.application.gateways.AppointmentEventGateway;
import com.diaghealthy_scheduling.domain.entities.Appointment;
import com.diaghealthy_scheduling.infrastructure.dtos.appointment.AppointmentCreatedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class AppointmentRabbitProducer implements AppointmentEventGateway {

    private static final String EXCHANGE = "appointment.exchange";
    private static final String ROUTING_KEY = "appointment.created";

    private final RabbitTemplate rabbitTemplate;

    public AppointmentRabbitProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishAppointmentCreated(Appointment appointment) {

        AppointmentCreatedEvent event = new AppointmentCreatedEvent(
                appointment.getId(),
                appointment.getPatientId(),
                appointment.getDoctorId(),
                appointment.getNurseId(),
                appointment.getScheduledAt(),
                appointment.getStatus().name(),
                appointment.getReason()
        );

        rabbitTemplate.convertAndSend(
                EXCHANGE,
                ROUTING_KEY,
                event
        );
    }
}