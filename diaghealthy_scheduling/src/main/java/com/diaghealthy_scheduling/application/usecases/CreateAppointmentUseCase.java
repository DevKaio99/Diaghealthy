package com.diaghealthy_scheduling.application.usecases;

import com.diaghealthy_scheduling.application.inputs.AppointmentCreateInput;
import com.diaghealthy_scheduling.domain.entities.Appointment;
import com.diaghealthy_scheduling.domain.repositories.AppointmentRepository;

public class CreateAppointmentUseCase {

    private final AppointmentRepository appointmentRepository;

    public CreateAppointmentUseCase(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public Appointment execute (AppointmentCreateInput input) {

        Appointment appointment = new Appointment(
                input.patientId(),
                input.doctorId(),
                input.nurseId(),
                input.scheduledAt(),
                input.reason()
        );

        return appointmentRepository.saveAppointment(appointment);
    }
}