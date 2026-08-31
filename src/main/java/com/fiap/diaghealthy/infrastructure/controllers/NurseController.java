package com.fiap.diaghealthy.infrastructure.controllers;

import com.fiap.diaghealthy.application.exceptions.UnauthorizedException;
import com.fiap.diaghealthy.application.usecases.CreateNurseUseCase;
import com.fiap.diaghealthy.application.usecases.UpdateNurseUseCase;
import com.fiap.diaghealthy.infrastructure.dtos.users.nurse.NurseCreateDTO;
import com.fiap.diaghealthy.infrastructure.dtos.users.nurse.NurseResponseDTO;
import com.fiap.diaghealthy.infrastructure.dtos.users.nurse.NurseUpdateDTO;
import com.fiap.diaghealthy.infrastructure.mappers.NurseMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/users/nurse")
public class NurseController {

    private final CreateNurseUseCase createNurseUseCase;
    private final UpdateNurseUseCase updateNurseUseCase;
    private final NurseMapper nurseMapper;

    public NurseController(CreateNurseUseCase createNurseUseCase, UpdateNurseUseCase updateNurseUseCase, NurseMapper nurseMapper) {
        this.createNurseUseCase = createNurseUseCase;
        this.updateNurseUseCase = updateNurseUseCase;
        this.nurseMapper = nurseMapper;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<NurseResponseDTO> create (@Valid @RequestBody NurseCreateDTO nurseCreateDTO) {

        var nurseInput = nurseMapper.toCreateInput(nurseCreateDTO);
        var nurseCreated = createNurseUseCase.execute(nurseInput);
        NurseResponseDTO nurseResponse = nurseMapper.toDto(nurseCreated);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nurseResponse);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'NURSE')")
    @PutMapping("/{id}")
    public ResponseEntity<NurseResponseDTO> update (
            @PathVariable ("id") UUID id,
            @RequestBody NurseUpdateDTO nurseUpdateDTO) throws UnauthorizedException {

        var nurseUpdateInput = nurseMapper.toUpdateInput(nurseUpdateDTO);
        var nurseUpdated = updateNurseUseCase.execute(id, nurseUpdateInput);
        NurseResponseDTO nurseResponseDTO = nurseMapper.toDto(nurseUpdated);

        return ResponseEntity.ok(nurseResponseDTO);

    }
}
