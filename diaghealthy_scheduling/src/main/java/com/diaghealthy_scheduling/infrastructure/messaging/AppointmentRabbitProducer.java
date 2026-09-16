package com.diaghealthy_scheduling.infrastructure.messaging;

import com.diaghealthy_scheduling.application.gateways.AppointmentEventGateway;
import com.diaghealthy_scheduling.domain.entities.Appointment;
import com.diaghealthy_scheduling.infrastructure.config.RabbitMQConfig;
import com.diaghealthy_scheduling.infrastructure.dtos.appointment.AppointmentEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class AppointmentRabbitProducer implements AppointmentEventGateway {

    private static final String CREATED_ROUTING_KEY = "appointment.created";
    private static final String UPDATED_ROUTING_KEY = "appointment.updated";

    private final RabbitTemplate rabbitTemplate;

    public AppointmentRabbitProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishAppointmentCreated(Appointment appointment, String patientEmail) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.APPOINTMENT_EXCHANGE,
                CREATED_ROUTING_KEY,
                toEvent(appointment, patientEmail)
        );
    }

    @Override
    public void publishAppointmentUpdated(Appointment appointment, String patientEmail) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.APPOINTMENT_EXCHANGE,
                UPDATED_ROUTING_KEY,
                toEvent(appointment, patientEmail)
        );
    }

    private AppointmentEvent toEvent(Appointment appointment, String patientEmail) {
        return new AppointmentEvent(
                appointment.getId(),
                appointment.getPatientId(),
                appointment.getDoctorId(),
                appointment.getNurseId(),
                appointment.getScheduledAt(),
                appointment.getStatus().name(),
                appointment.getReason(),
                patientEmail
        );
    }
}
