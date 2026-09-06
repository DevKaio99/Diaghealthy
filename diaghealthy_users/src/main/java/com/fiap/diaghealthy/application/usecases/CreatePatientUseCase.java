package com.fiap.diaghealthy.application.usecases;

import com.fiap.diaghealthy.application.exceptions.BusinessException;
import com.fiap.diaghealthy.application.inputs.PatientCreateInput;
import com.fiap.diaghealthy.domain.entities.Patient;
import com.fiap.diaghealthy.domain.repositories.PatientRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CreatePatientUseCase {

    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;


    public CreatePatientUseCase(PatientRepository patientRepository, PasswordEncoder passwordEncoder) {
        this.patientRepository = patientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Patient execute (PatientCreateInput patientCreateInput) {
        if (patientRepository.validateEmailExists(patientCreateInput.email())) {
            throw new BusinessException("Email já cadastrado");
        }

        String passwordHash = passwordEncoder.encode(patientCreateInput.password());

        Patient patient = new Patient(
                patientCreateInput.name(),
                patientCreateInput.email(),
                passwordHash,
                patientCreateInput.dateOfBirth()
        );

        return patientRepository.save(patient);
    }
}
