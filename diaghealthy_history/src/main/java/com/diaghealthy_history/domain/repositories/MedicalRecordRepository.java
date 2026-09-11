package com.diaghealthy_history.domain.repositories;

import com.diaghealthy_history.domain.entities.MedicalRecord;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MedicalRecordRepository {
    MedicalRecord saveRecord(MedicalRecord record);
    MedicalRecord updateRecord(MedicalRecord record);
    Optional<MedicalRecord> findRecordById(UUID id);
    List<MedicalRecord> findRecordsByPatientId(UUID patientId);
    List<MedicalRecord> findFutureRecordsByPatientId(UUID patientId, LocalDateTime from);
}
