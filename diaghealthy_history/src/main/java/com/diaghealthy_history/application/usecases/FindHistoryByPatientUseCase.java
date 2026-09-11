package com.diaghealthy_history.application.usecases;

import com.diaghealthy_history.domain.entities.MedicalRecord;
import com.diaghealthy_history.domain.repositories.MedicalRecordRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class FindHistoryByPatientUseCase {

    private final MedicalRecordRepository medicalRecordRepository;

    public FindHistoryByPatientUseCase(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }

    public List<MedicalRecord> execute(UUID patientId, boolean onlyFuture) {

        if (onlyFuture) {
            return medicalRecordRepository.findFutureRecordsByPatientId(patientId, LocalDateTime.now());
        }

        return medicalRecordRepository.findRecordsByPatientId(patientId);
    }
}
