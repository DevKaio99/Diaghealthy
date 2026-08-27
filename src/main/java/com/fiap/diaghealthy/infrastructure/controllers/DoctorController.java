package com.fiap.diaghealthy.infrastructure.controllers;

import com.fiap.diaghealthy.application.exceptions.UnauthorizedException;
import com.fiap.diaghealthy.application.usecases.CreateDoctorUseCase;
import com.fiap.diaghealthy.application.usecases.UpdateDoctorUseCase;
import com.fiap.diaghealthy.infrastructure.dtos.user.doctor.DoctorCreateDTO;
import com.fiap.diaghealthy.infrastructure.dtos.user.doctor.DoctorResponseDTO;
import com.fiap.diaghealthy.infrastructure.dtos.user.doctor.DoctorUpdateDTO;
import com.fiap.diaghealthy.infrastructure.mappers.DoctorMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/users/doctor")
public class DoctorController {

    private final CreateDoctorUseCase createDoctorUseCase;
    private final UpdateDoctorUseCase updateDoctorUseCase;
    private final DoctorMapper doctorMapper;

    public DoctorController(CreateDoctorUseCase createDoctorUseCase, UpdateDoctorUseCase updateDoctorUseCase, DoctorMapper doctorMapper) {
        this.createDoctorUseCase = createDoctorUseCase;
        this.updateDoctorUseCase = updateDoctorUseCase;
        this.doctorMapper = doctorMapper;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<DoctorResponseDTO> create (@Valid @RequestBody DoctorCreateDTO doctorCreateDTO) {

        var doctorInput = doctorMapper.toCreateInput(doctorCreateDTO);
        var doctorCreated = createDoctorUseCase.execute(doctorInput);
        DoctorResponseDTO doctorResponse = doctorMapper.toDto(doctorCreated);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(doctorResponse);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    @PutMapping("/{id}")
    public ResponseEntity<DoctorResponseDTO> update (
            @PathVariable ("id") UUID id,
            @RequestBody DoctorUpdateDTO doctorUpdateDTO) throws UnauthorizedException {

        var doctorUpdateInput = doctorMapper.toUpdateInput(doctorUpdateDTO);
        var doctorUpdated = updateDoctorUseCase.execute(id, doctorUpdateInput);
        DoctorResponseDTO doctorResponseDTO = doctorMapper.toDto(doctorUpdated);

        return ResponseEntity.ok(doctorResponseDTO);

    }
}
