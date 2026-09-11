package com.diaghealthy_history.application.usecases;

import com.diaghealthy_history.application.gateways.UserServiceGateway;
import com.diaghealthy_history.application.inputs.RegisterHistoryInput;
import com.diaghealthy_history.domain.entities.MedicalRecord;
import com.diaghealthy_history.domain.enuns.HistoryStatus;
import com.diaghealthy_history.domain.repositories.MedicalRecordRepository;

public class RegisterHistoryUseCase {

    private final MedicalRecordRepository medicalRecordRepository;
    private final UserServiceGateway userServiceGateway;

    public RegisterHistoryUseCase(MedicalRecordRepository medicalRecordRepository, UserServiceGateway userServiceGateway) {
        this.medicalRecordRepository = medicalRecordRepository;
        this.userServiceGateway = userServiceGateway;
    }

    public MedicalRecord execute(RegisterHistoryInput input) {

        userServiceGateway.findPatientById(input.patientId());
        userServiceGateway.findDoctorById(input.doctorId());

        if (input.nurseId() != null) {
            userServiceGateway.findNurseById(input.nurseId());
        }

        HistoryStatus status = input.status() != null ? input.status() : HistoryStatus.SCHEDULED;

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
}
