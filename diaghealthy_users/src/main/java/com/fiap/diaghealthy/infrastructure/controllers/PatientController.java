package com.fiap.diaghealthy.infrastructure.controllers;

import com.fiap.diaghealthy.application.exceptions.UnauthorizedException;
import com.fiap.diaghealthy.application.usecases.CreatePatientUseCase;
import com.fiap.diaghealthy.application.usecases.UpdatePatientUseCase;
import com.fiap.diaghealthy.infrastructure.dtos.users.patient.PatientCreateDTO;
import com.fiap.diaghealthy.infrastructure.dtos.users.patient.PatientResponseDTO;
import com.fiap.diaghealthy.infrastructure.dtos.users.patient.PatientUpdateDTO;
import com.fiap.diaghealthy.infrastructure.mappers.PatientMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/users/patient")
public class PatientController {

    private final CreatePatientUseCase createPatientUseCase;
    private final UpdatePatientUseCase updatePatientUseCase;
    private final PatientMapper patientMapper;

    public PatientController(CreatePatientUseCase createPatientUseCase, UpdatePatientUseCase updatePatientUseCase, PatientMapper patientMapper) {
        this.createPatientUseCase = createPatientUseCase;
        this.updatePatientUseCase = updatePatientUseCase;
        this.patientMapper = patientMapper;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'NURSE', 'DOCTOR')")
    @PostMapping
    public ResponseEntity<PatientResponseDTO> create (@Valid @RequestBody PatientCreateDTO patientCreateDTO) {

        var patientInput = patientMapper.toCreateInput(patientCreateDTO);
        var patientCreated = createPatientUseCase.execute(patientInput);
        PatientResponseDTO patientResponse = patientMapper.toDto(patientCreated);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(patientResponse);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE')")
    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> update (
            @PathVariable ("id") UUID id,
            @RequestBody PatientUpdateDTO patientUpdateDTO) throws UnauthorizedException {

        var patientUpdateInput = patientMapper.toUpdateInput(patientUpdateDTO);
        var patientUpdated = updatePatientUseCase.execute(id, patientUpdateInput);
        PatientResponseDTO patientResponseDTO = patientMapper.toDto(patientUpdated);

        return ResponseEntity.ok(patientResponseDTO);

    }
}
