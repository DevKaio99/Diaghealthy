package com.diaghealthy_scheduling.application.usecases;

import com.diaghealthy_scheduling.application.gateways.UserServiceGateway;
import com.diaghealthy_scheduling.application.inputs.AppointmentCreateInput;
import com.diaghealthy_scheduling.domain.entities.Appointment;
import com.diaghealthy_scheduling.domain.repositories.AppointmentRepository;

public class CreateAppointmentUseCase {

    private final AppointmentRepository appointmentRepository;
    private final UserServiceGateway userServiceGateway;

    public CreateAppointmentUseCase(AppointmentRepository appointmentRepository, UserServiceGateway userServiceGateway) {
        this.appointmentRepository = appointmentRepository;
        this.userServiceGateway = userServiceGateway;
    }

    public Appointment execute(AppointmentCreateInput input) {

        userServiceGateway.findPatientById(input.patientId());
        userServiceGateway.findDoctorById(input.doctorId());

        if (input.nurseId() != null) {
            userServiceGateway.findNurseById(input.nurseId());
        }


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