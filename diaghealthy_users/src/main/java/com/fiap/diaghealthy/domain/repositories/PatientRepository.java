package com.fiap.diaghealthy.domain.repositories;

import com.fiap.diaghealthy.domain.entities.Patient;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PatientRepository {
    Patient save (Patient patient);
    boolean validateEmailExists (String email);
    List<Patient> patientList();
    Optional<Patient> findPatientById (UUID id);
    Patient updatePatient (Patient patient);
    Optional <Patient> findByEmailIgnoreCase (String email);
}
