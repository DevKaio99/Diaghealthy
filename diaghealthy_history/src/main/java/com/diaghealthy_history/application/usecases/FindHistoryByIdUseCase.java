package com.diaghealthy_history.application.usecases;

import com.diaghealthy_history.application.exceptions.ResourceNotFoundException;
import com.diaghealthy_history.domain.entities.MedicalRecord;
import com.diaghealthy_history.domain.repositories.MedicalRecordRepository;

import java.util.UUID;

public class FindHistoryByIdUseCase {

    private final MedicalRecordRepository medicalRecordRepository;

    public FindHistoryByIdUseCase(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }

    public MedicalRecord execute(UUID id) {
        return medicalRecordRepository.findRecordById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro de histórico não encontrado"));
    }
}
