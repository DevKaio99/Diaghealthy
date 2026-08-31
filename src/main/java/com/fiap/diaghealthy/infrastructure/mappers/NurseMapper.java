package com.fiap.diaghealthy.infrastructure.mappers;

import com.fiap.diaghealthy.application.inputs.NurseCreateInput;
import com.fiap.diaghealthy.application.inputs.NurseUpdateInput;
import com.fiap.diaghealthy.domain.entities.Nurse;
import com.fiap.diaghealthy.infrastructure.dtos.users.nurse.NurseCreateDTO;
import com.fiap.diaghealthy.infrastructure.dtos.users.nurse.NurseResponseDTO;
import com.fiap.diaghealthy.infrastructure.dtos.users.nurse.NurseUpdateDTO;
import org.springframework.stereotype.Component;

@Component
public class NurseMapper {
    public NurseCreateInput toCreateInput (NurseCreateDTO nurseCreateDTO) {
        return new NurseCreateInput(
                nurseCreateDTO.name(),
                nurseCreateDTO.email(),
                nurseCreateDTO.password(),
                nurseCreateDTO.coren()
        );
    }

    public NurseResponseDTO toDto (Nurse nurse) {
        return new NurseResponseDTO(
                nurse.getId(),
                nurse.getName(),
                nurse.getEmail(),
                nurse.getCoren(),
                nurse.getDateLastUpdate(),
                nurse.getCreatedAt(),
                true,
                nurse.getRole()
        );
    }

    public NurseUpdateInput toUpdateInput (NurseUpdateDTO nurseUpdateDTO) {
        return new NurseUpdateInput(
                nurseUpdateDTO.name(),
                nurseUpdateDTO.email(),
                nurseUpdateDTO.coren(),
                nurseUpdateDTO.isActive()

        );
    }
    
}
