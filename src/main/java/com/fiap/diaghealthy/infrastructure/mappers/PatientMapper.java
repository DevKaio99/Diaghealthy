package com.fiap.diaghealthy.infrastructure.mappers;

import com.fiap.diaghealthy.application.inputs.PatientCreateInput;
import com.fiap.diaghealthy.application.inputs.PatientUpdateInput;
import com.fiap.diaghealthy.domain.entities.Patient;
import com.fiap.diaghealthy.infrastructure.dtos.users.patient.PatientCreateDTO;
import com.fiap.diaghealthy.infrastructure.dtos.users.patient.PatientResponseDTO;
import com.fiap.diaghealthy.infrastructure.dtos.users.patient.PatientUpdateDTO;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper {
    public PatientCreateInput toCreateInput (PatientCreateDTO patientCreateDTO) {
        return new PatientCreateInput(
                patientCreateDTO.name(),
                patientCreateDTO.email(),
                patientCreateDTO.password(),
                patientCreateDTO.dateOfBirth()
        );
    }

    public PatientResponseDTO toDto (Patient patient) {
        return new PatientResponseDTO(
                patient.getId(),
                patient.getName(),
                patient.getEmail(),
                patient.getDateOfBirth(),
                patient.getDateLastUpdate(),
                patient.getCreatedAt(),
                true,
                patient.getRole()
        );
    }

    public PatientUpdateInput toUpdateInput (PatientUpdateDTO patientUpdateDTO) {
        return new PatientUpdateInput(
                patientUpdateDTO.name(),
                patientUpdateDTO.email(),
                patientUpdateDTO.dateOfBirth(),
                patientUpdateDTO.isActive()
        );
    }
}
