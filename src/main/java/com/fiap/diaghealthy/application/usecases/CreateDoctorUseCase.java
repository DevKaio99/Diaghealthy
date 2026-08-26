package com.fiap.diaghealthy.application.usecases;

import com.fiap.diaghealthy.application.exceptions.BusinessException;
import com.fiap.diaghealthy.application.inputs.DoctorCreateInput;
import com.fiap.diaghealthy.domain.entities.Doctor;
import com.fiap.diaghealthy.domain.repositories.DoctorRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CreateDoctorUseCase {

    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;


    public CreateDoctorUseCase(DoctorRepository doctorRepository, PasswordEncoder passwordEncoder) {
        this.doctorRepository = doctorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Doctor execute (DoctorCreateInput doctorCreateInput) {
        if (doctorRepository.validateEmailExists(doctorCreateInput.email())) {
            throw new BusinessException("Email já cadastrado");
        }

        String passwordHash = passwordEncoder.encode(doctorCreateInput.password());

        Doctor doctor = new Doctor(
                doctorCreateInput.name(),
                doctorCreateInput.email(),
                passwordHash,
                doctorCreateInput.crm(),
                doctorCreateInput.speciality()
        );

        return doctorRepository.save(doctor);
    }
}
