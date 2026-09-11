package com.fiap.diaghealthy.infrastructure.controllers;

import com.fiap.diaghealthy.application.exceptions.UnauthorizedException;
import com.fiap.diaghealthy.application.usecases.doctor.CreateDoctorUseCase;
import com.fiap.diaghealthy.application.usecases.doctor.UpdateDoctorUseCase;
import com.fiap.diaghealthy.infrastructure.dtos.users.doctor.DoctorCreateDTO;
import com.fiap.diaghealthy.infrastructure.dtos.users.doctor.DoctorResponseDTO;
import com.fiap.diaghealthy.infrastructure.dtos.users.doctor.DoctorUpdateDTO;
import com.fiap.diaghealthy.infrastructure.mappers.DoctorMapper;
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
@RequestMapping("api/v1/users/doctor")
@Tag(name = "Médicos", description = "Gerenciamento de médicos")
public class DoctorController {

    private final CreateDoctorUseCase createDoctorUseCase;
    private final UpdateDoctorUseCase updateDoctorUseCase;
    private final DoctorMapper doctorMapper;

    public DoctorController(CreateDoctorUseCase createDoctorUseCase, UpdateDoctorUseCase updateDoctorUseCase, DoctorMapper doctorMapper) {
        this.createDoctorUseCase = createDoctorUseCase;
        this.updateDoctorUseCase = updateDoctorUseCase;
        this.doctorMapper = doctorMapper;
    }

    @Operation(
            summary = "Cadastrar médico",
            description = "Cria um novo médico no sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Médico criado",
                    content = @Content(schema = @Schema(implementation = DoctorResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            )
    })
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

    @Operation(
            summary = "Atualizar médico",
            description = "Atualiza os dados do médico especificado pelo ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Médico atualizado",
                    content = @Content(schema = @Schema(implementation = DoctorResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Médico não encontrado",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            )
    })
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
