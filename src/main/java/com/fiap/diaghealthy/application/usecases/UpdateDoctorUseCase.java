package com.fiap.diaghealthy.application.usecases;

import com.fiap.diaghealthy.application.exceptions.BusinessException;
import com.fiap.diaghealthy.application.exceptions.ResourceNotFoundException;
import com.fiap.diaghealthy.application.inputs.DoctorUpdateInput;
import com.fiap.diaghealthy.domain.entities.Doctor;
import com.fiap.diaghealthy.domain.repositories.DoctorRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public class UpdateDoctorUseCase {

    private final DoctorRepository doctorRepository;

    public UpdateDoctorUseCase(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Doctor execute(UUID id, DoctorUpdateInput doctorUpdateInput) {
        Doctor doctor = doctorRepository.findDoctorById(id).
                orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        if (!doctor.getEmail().equals(doctorUpdateInput.email())
                && doctorRepository.validateEmailExists(doctorUpdateInput.email())) {
            throw new BusinessException("Email já cadastrado.");
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
