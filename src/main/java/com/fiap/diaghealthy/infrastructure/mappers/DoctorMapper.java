package com.fiap.diaghealthy.infrastructure.mappers;

import com.fiap.diaghealthy.application.inputs.DoctorCreateInput;
import com.fiap.diaghealthy.domain.entities.Doctor;
import com.fiap.diaghealthy.infrastructure.dtos.user.doctor.DoctorCreateDTO;
import com.fiap.diaghealthy.infrastructure.dtos.user.doctor.DoctorResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class DoctorMapper {

    public DoctorCreateInput toInput (DoctorCreateDTO doctorCreateDTO) {
        return new DoctorCreateInput(
                doctorCreateDTO.name(),
                doctorCreateDTO.email(),
                doctorCreateDTO.password(),
                doctorCreateDTO.crm(),
                doctorCreateDTO.speciality()
        );
    }

    public DoctorResponseDTO toDto (Doctor doctor) {
        return new DoctorResponseDTO(
                doctor.getId(),
                doctor.getName(),
                doctor.getEmail(),
                doctor.getDateLastUpdate(),
                doctor.getCreatedAt(),
                doctor.getCRM(),
                doctor.getSpeciality(),
                true,
                doctor.getRole()
        );
    }
}
