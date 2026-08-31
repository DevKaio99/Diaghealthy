package com.fiap.diaghealthy.application.usecases;

import com.fiap.diaghealthy.application.exceptions.BusinessException;
import com.fiap.diaghealthy.application.inputs.NurseCreateInput;
import com.fiap.diaghealthy.domain.entities.Nurse;
import com.fiap.diaghealthy.domain.repositories.NurseRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CreateNurseUseCase {

    private final NurseRepository nurseRepository;
    private final PasswordEncoder passwordEncoder;


    public CreateNurseUseCase(NurseRepository nurseRepository, PasswordEncoder passwordEncoder) {
        this.nurseRepository = nurseRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Nurse execute (NurseCreateInput nurseCreateInput) {
        if (nurseRepository.validateEmailExists(nurseCreateInput.email())) {
            throw new BusinessException("Email já cadastrado");
        }

        if (nurseRepository.findNurseByCoren(nurseCreateInput.coren())) {
            throw new BusinessException("Coren já cadastrado");
        }

        String passwordHash = passwordEncoder.encode(nurseCreateInput.password());

        Nurse nurse = new Nurse(
                nurseCreateInput.name(),
                nurseCreateInput.email(),
                passwordHash,
                nurseCreateInput.coren()
        );

        return nurseRepository.save(nurse);
    }
}
