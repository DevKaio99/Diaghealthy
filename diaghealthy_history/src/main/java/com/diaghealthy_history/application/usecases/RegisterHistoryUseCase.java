package com.diaghealthy_history.application.usecases;

import com.diaghealthy_history.application.gateways.UserServiceGateway;
import com.diaghealthy_history.application.inputs.RegisterHistoryInput;
import com.diaghealthy_history.domain.entities.MedicalRecord;
import com.diaghealthy_history.domain.enuns.HistoryStatus;
import com.diaghealthy_history.domain.repositories.MedicalRecordRepository;

import java.time.LocalDateTime;

public class RegisterHistoryUseCase {

    private final MedicalRecordRepository medicalRecordRepository;
    private final UserServiceGateway userServiceGateway;

    public RegisterHistoryUseCase(MedicalRecordRepository medicalRecordRepository, UserServiceGateway userServiceGateway) {
        this.medicalRecordRepository = medicalRecordRepository;
        this.userServiceGateway = userServiceGateway;
    }

    // Cadastro manual via REST: valida paciente/médico/enfermeiro no diaghealthy_users.
    public MedicalRecord execute(RegisterHistoryInput input) {

        userServiceGateway.findPatientById(input.patientId());
        userServiceGateway.findDoctorById(input.doctorId());

        if (input.nurseId() != null) {
            userServiceGateway.findNurseById(input.nurseId());
        }

        return registerOrUpdate(input);
    }

    // Consumidor do RabbitMQ: o agendamento já foi validado pelo scheduling, não revalida aqui.
    public MedicalRecord executeFromEvent(RegisterHistoryInput input) {
        return registerOrUpdate(input);
    }

    private MedicalRecord registerOrUpdate(RegisterHistoryInput input) {

        HistoryStatus status = input.status() != null ? input.status() : HistoryStatus.SCHEDULED;

        return medicalRecordRepository.findRecordByAppointmentId(input.appointmentId())
                .map(existing -> updateExisting(existing, input, status))
                .orElseGet(() -> createNew(input, status));
    }

    private MedicalRecord createNew(RegisterHistoryInput input, HistoryStatus status) {

        MedicalRecord record = new MedicalRecord(
                input.appointmentId(),
                input.patientId(),
                input.doctorId(),
                input.nurseId(),
                input.scheduledAt(),
                status,
                input.reason()
        );

        return medicalRecordRepository.saveRecord(record);
    }

    private MedicalRecord updateExisting(MedicalRecord existing, RegisterHistoryInput input, HistoryStatus status) {

        existing.setScheduledAt(input.scheduledAt());
        existing.setStatus(status);
        existing.setReason(input.reason());
        existing.setUpdatedAt(LocalDateTime.now());

        return medicalRecordRepository.updateRecord(existing);
    }
}
