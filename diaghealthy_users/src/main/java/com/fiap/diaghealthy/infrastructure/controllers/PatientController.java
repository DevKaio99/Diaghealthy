package com.fiap.diaghealthy.infrastructure.controllers;

import com.fiap.diaghealthy.application.exceptions.UnauthorizedException;
import com.fiap.diaghealthy.application.usecases.patient.CreatePatientUseCase;
import com.fiap.diaghealthy.application.usecases.patient.UpdatePatientUseCase;
import com.fiap.diaghealthy.infrastructure.dtos.users.patient.PatientCreateDTO;
import com.fiap.diaghealthy.infrastructure.dtos.users.patient.PatientResponseDTO;
import com.fiap.diaghealthy.infrastructure.dtos.users.patient.PatientUpdateDTO;
import com.fiap.diaghealthy.infrastructure.mappers.PatientMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/users/patient")
@Tag(name = "Pacientes", description = "Gerenciamento de pacientes")
public class PatientController {

    private final CreatePatientUseCase createPatientUseCase;
    private final UpdatePatientUseCase updatePatientUseCase;
    private final PatientMapper patientMapper;

    public PatientController(CreatePatientUseCase createPatientUseCase, UpdatePatientUseCase updatePatientUseCase, PatientMapper patientMapper) {
        this.createPatientUseCase = createPatientUseCase;
        this.updatePatientUseCase = updatePatientUseCase;
        this.patientMapper = patientMapper;
    }

    @Operation(
            summary = "Cadastrar paciente",
            description = "Cria um novo paciente no sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Paciente criado",
                    content = @Content(schema = @Schema(implementation = PatientResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            )
    })
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

    @Operation(
            summary = "Atualizar paciente",
            description = "Atualiza os dados do paciente especificado pelo ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Paciente atualizado",
                    content = @Content(schema = @Schema(implementation = PatientResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Paciente não encontrado",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            )
    })
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
