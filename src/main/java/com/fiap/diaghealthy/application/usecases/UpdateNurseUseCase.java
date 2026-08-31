package com.fiap.diaghealthy.application.usecases;

import com.fiap.diaghealthy.application.exceptions.BusinessException;
import com.fiap.diaghealthy.application.exceptions.ResourceNotFoundException;
import com.fiap.diaghealthy.application.exceptions.UnauthorizedException;
import com.fiap.diaghealthy.application.inputs.NurseUpdateInput;
import com.fiap.diaghealthy.application.services.CurrentUser;
import com.fiap.diaghealthy.domain.entities.Nurse;
import com.fiap.diaghealthy.domain.enuns.Role;
import com.fiap.diaghealthy.domain.repositories.NurseRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public class UpdateNurseUseCase {

    private final NurseRepository nurseRepository;
    private final CurrentUser currentUser;

    public UpdateNurseUseCase(NurseRepository nurseRepository, CurrentUser currentUser) {
        this.nurseRepository = nurseRepository;
        this.currentUser = currentUser;
    }

    public Nurse execute(UUID id, NurseUpdateInput nurseUpdateInput) throws UnauthorizedException {
        Nurse nurse = nurseRepository.findNurseById(id).
                orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        UUID loggedUserId = currentUser.getId();
        Role loggedUserRole = currentUser.getRole();

        if (!loggedUserId.equals(id) && loggedUserRole != Role.ADMIN) {
            throw new UnauthorizedException("Usuário não possui permissão para alterar este usuário");
        }

        if (!nurse.getEmail().equals(nurseUpdateInput.email())
                && nurseRepository.validateEmailExists(nurseUpdateInput.email())) {
            throw new BusinessException("Email já cadastrado.");
        }

        if (!nurse.getCoren().equals(nurseUpdateInput.coren())
                && nurseRepository.findNurseByCoren(nurseUpdateInput.coren())) {
            throw new BusinessException("CRM já cadastrado.");
        }

        nurse.setName(nurseUpdateInput.name());
        nurse.setEmail(nurseUpdateInput.email());
        nurse.setActive(nurseUpdateInput.isActive());
        nurse.setCoren(nurseUpdateInput.coren());
        nurse.setDateLastUpdate(LocalDateTime.now());

        return nurseRepository.updateNurse(nurse);
    }


}
