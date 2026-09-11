package com.diaghealthy_history.application.usecases;

import com.diaghealthy_history.application.exceptions.ResourceNotFoundException;
import com.diaghealthy_history.application.inputs.UpdateHistoryInput;
import com.diaghealthy_history.domain.entities.MedicalRecord;
import com.diaghealthy_history.domain.repositories.MedicalRecordRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public class UpdateHistoryUseCase {

    private final MedicalRecordRepository medicalRecordRepository;

    public UpdateHistoryUseCase(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }

    public MedicalRecord execute(UUID id, UpdateHistoryInput input) {

        MedicalRecord record = medicalRecordRepository.findRecordById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Registro de histórico não encontrado")
                );

        if (input.status() != null) {
            record.setStatus(input.status());
        }

        if (input.notes() != null && !input.notes().isBlank()) {
            record.setNotes(input.notes());
        }

        record.setUpdatedAt(LocalDateTime.now());

        return medicalRecordRepository.updateRecord(record);
    }
}
