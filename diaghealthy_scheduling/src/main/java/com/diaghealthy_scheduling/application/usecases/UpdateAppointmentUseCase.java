package com.diaghealthy_scheduling.application.usecases;

import com.diaghealthy_scheduling.application.exceptions.ResourceNotFoundException;
import com.diaghealthy_scheduling.application.inputs.AppointmentUpdateInput;
import com.diaghealthy_scheduling.domain.entities.Appointment;
import com.diaghealthy_scheduling.domain.repositories.AppointmentRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public class UpdateAppointmentUseCase {

    private final AppointmentRepository appointmentRepository;

    public UpdateAppointmentUseCase(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public Appointment execute(UUID id, AppointmentUpdateInput input) {

        Appointment appointment = appointmentRepository.findAppointmentById(input.id())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Agendamento não encontrado")
                );

        appointment.setScheduledAt(input.scheduledAt());
        appointment.setStatus(input.status());
        appointment.setReason(input.reason());
        appointment.setUpdatedAt(LocalDateTime.now());

        return appointmentRepository.updateAppointment(appointment);
    }
}