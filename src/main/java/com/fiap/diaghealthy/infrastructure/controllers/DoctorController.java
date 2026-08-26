package com.fiap.diaghealthy.infrastructure.controllers;

import com.fiap.diaghealthy.application.usecases.CreateDoctorUseCase;
import com.fiap.diaghealthy.infrastructure.dtos.user.doctor.DoctorCreateDTO;
import com.fiap.diaghealthy.infrastructure.dtos.user.doctor.DoctorResponseDTO;
import com.fiap.diaghealthy.infrastructure.mappers.DoctorMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users/doctor")
public class DoctorController {

    private final CreateDoctorUseCase createDoctorUseCase;
    private final DoctorMapper doctorMapper;

    public DoctorController(CreateDoctorUseCase createDoctorUseCase, DoctorMapper doctorMapper) {
        this.createDoctorUseCase = createDoctorUseCase;
        this.doctorMapper = doctorMapper;
    }

    @PostMapping
    public ResponseEntity<DoctorResponseDTO> create (@Valid @RequestBody DoctorCreateDTO doctorCreateDTO) {

        var doctorInput = doctorMapper.toInput(doctorCreateDTO);
        var doctorCreated = createDoctorUseCase.execute(doctorInput);
        DoctorResponseDTO doctorResponse = doctorMapper.toDto(doctorCreated);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(doctorResponse);
    }
}
