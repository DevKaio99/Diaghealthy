package com.fiap.diaghealthy.application.usecases;

import com.fiap.diaghealthy.application.exceptions.BusinessException;
import com.fiap.diaghealthy.application.exceptions.ResourceNotFoundException;
import com.fiap.diaghealthy.application.exceptions.UnauthorizedException;
import com.fiap.diaghealthy.application.inputs.DoctorUpdateInput;
import com.fiap.diaghealthy.application.services.CurrentUser;
import com.fiap.diaghealthy.domain.entities.Doctor;
import com.fiap.diaghealthy.domain.enuns.Role;
import com.fiap.diaghealthy.domain.repositories.DoctorRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public class UpdateDoctorUseCase {

    private final DoctorRepository doctorRepository;
    private final CurrentUser currentUser;

    public UpdateDoctorUseCase(DoctorRepository doctorRepository, CurrentUser currentUser) {
        this.doctorRepository = doctorRepository;
        this.currentUser = currentUser;
    }

    public Doctor execute(UUID id, DoctorUpdateInput doctorUpdateInput) throws UnauthorizedException {
        Doctor doctor = doctorRepository.findDoctorById(id).
                orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        UUID loggedUserId = currentUser.getId();
        Role loggedUserRole = currentUser.getRole();

        if (!loggedUserId.equals(id) && loggedUserRole != Role.ADMIN) {
            throw new UnauthorizedException("Usuário não possui permissão para alterar este usuário");
        }

        if (!doctor.getEmail().equals(doctorUpdateInput.email())
                && doctorRepository.validateEmailExists(doctorUpdateInput.email())) {
            throw new BusinessException("Email já cadastrado.");
        }

        if (!doctor.getCRM().equals(doctorUpdateInput.crm())
        && doctorRepository.findDoctorByCRM(doctorUpdateInput.crm())) {
            throw new BusinessException("CRM já cadastrado.");
        }

        doctor.setName(doctorUpdateInput.name());
        doctor.setEmail(doctorUpdateInput.email());
        doctor.setActive(doctorUpdateInput.isActive());
        doctor.setCRM(doctorUpdateInput.crm());
        doctor.setSpeciality(doctorUpdateInput.speciality());
        doctor.setDateLastUpdate(LocalDateTime.now());

        return doctorRepository.updateDoctor(doctor);
    }


}
