package com.fiap.diaghealthy.application.usecases;

import com.fiap.diaghealthy.application.exceptions.BusinessException;
import com.fiap.diaghealthy.application.exceptions.ResourceNotFoundException;
import com.fiap.diaghealthy.application.exceptions.UnauthorizedException;
import com.fiap.diaghealthy.application.inputs.PatientUpdateInput;
import com.fiap.diaghealthy.application.services.CurrentUser;
import com.fiap.diaghealthy.domain.entities.Patient;
import com.fiap.diaghealthy.domain.enuns.Role;
import com.fiap.diaghealthy.domain.repositories.PatientRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public class UpdatePatientUseCase {

    private final PatientRepository patientRepository;
    private final CurrentUser currentUser;

    public UpdatePatientUseCase(PatientRepository patientRepository, CurrentUser currentUser) {
        this.patientRepository = patientRepository;
        this.currentUser = currentUser;
    }

    public Patient execute(UUID id, PatientUpdateInput patientUpdateInput) throws UnauthorizedException {
        Patient patient = patientRepository.findPatientById(id).
                orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        UUID loggedUserId = currentUser.getId();
        Role loggedUserRole = currentUser.getRole();

        if (!loggedUserId.equals(id) && loggedUserRole != Role.ADMIN) {
            throw new UnauthorizedException("Usuário não possui permissão para alterar este usuário");
        }

        if (!patient.getEmail().equals(patientUpdateInput.email())
                && patientRepository.validateEmailExists(patientUpdateInput.email())) {
            throw new BusinessException("Email já cadastrado.");
        }

        patient.setName(patientUpdateInput.name());
        patient.setEmail(patientUpdateInput.email());
        patient.setActive(patientUpdateInput.isActive());
        patient.setDateOfBirth(patientUpdateInput.dateOfBirth());
        patient.setDateLastUpdate(LocalDateTime.now());

        return patientRepository.updatePatient(patient);
    }


}
