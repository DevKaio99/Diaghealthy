package com.diaghealthy_scheduling.application.usecases;

import com.diaghealthy_scheduling.application.exceptions.ResourceNotFoundException;
import com.diaghealthy_scheduling.application.gateways.AppointmentEventGateway;
import com.diaghealthy_scheduling.application.gateways.UserResponse;
import com.diaghealthy_scheduling.application.gateways.UserServiceGateway;
import com.diaghealthy_scheduling.application.inputs.AppointmentUpdateInput;
import com.diaghealthy_scheduling.domain.entities.Appointment;
import com.diaghealthy_scheduling.domain.repositories.AppointmentRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public class UpdateAppointmentUseCase {

    private final AppointmentRepository appointmentRepository;
    private final UserServiceGateway userServiceGateway;
    private final AppointmentEventGateway appointmentEventGateway;

    public UpdateAppointmentUseCase(
            AppointmentRepository appointmentRepository,
            UserServiceGateway userServiceGateway,
            AppointmentEventGateway appointmentEventGateway
    ) {
        this.appointmentRepository = appointmentRepository;
        this.userServiceGateway = userServiceGateway;
        this.appointmentEventGateway = appointmentEventGateway;
    }

    public Appointment execute(UUID id, AppointmentUpdateInput input) {

        Appointment appointment = appointmentRepository.findAppointmentById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Agendamento não encontrado")
                );

        if (input.scheduledAt() != null) {
            appointment.setScheduledAt(input.scheduledAt());
        }

        if (input.status() != null) {
            appointment.setStatus(input.status());
        }

        if (input.reason() != null && !input.reason().isBlank()) {
            appointment.setReason(input.reason());
        }

        appointment.setUpdatedAt(LocalDateTime.now());

        Appointment updatedAppointment = appointmentRepository.updateAppointment(appointment);

        UserResponse patient = userServiceGateway.findPatientById(updatedAppointment.getPatientId());

        appointmentEventGateway.publishAppointmentUpdated(updatedAppointment, patient.email());

        return updatedAppointment;
    }
}