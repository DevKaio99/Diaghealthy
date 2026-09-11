package com.fiap.diaghealthy.infrastructure.controllers;

import com.fiap.diaghealthy.application.exceptions.UnauthorizedException;
import com.fiap.diaghealthy.application.usecases.nurse.CreateNurseUseCase;
import com.fiap.diaghealthy.application.usecases.nurse.UpdateNurseUseCase;
import com.fiap.diaghealthy.infrastructure.dtos.users.nurse.NurseCreateDTO;
import com.fiap.diaghealthy.infrastructure.dtos.users.nurse.NurseResponseDTO;
import com.fiap.diaghealthy.infrastructure.dtos.users.nurse.NurseUpdateDTO;
import com.fiap.diaghealthy.infrastructure.mappers.NurseMapper;
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
@RequestMapping("api/v1/users/nurse")
@Tag(name = "Enfermeiros", description = "Gerenciamento de enfermeiros")
public class NurseController {

    private final CreateNurseUseCase createNurseUseCase;
    private final UpdateNurseUseCase updateNurseUseCase;
    private final NurseMapper nurseMapper;

    public NurseController(CreateNurseUseCase createNurseUseCase, UpdateNurseUseCase updateNurseUseCase, NurseMapper nurseMapper) {
        this.createNurseUseCase = createNurseUseCase;
        this.updateNurseUseCase = updateNurseUseCase;
        this.nurseMapper = nurseMapper;
    }

    @Operation(
            summary = "Cadastrar enfermeiro",
            description = "Cria um novo enfermeiro no sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Enfermeiro criado",
                    content = @Content(schema = @Schema(implementation = NurseResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            )
    })
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

    @Operation(
            summary = "Atualizar enfermeiro",
            description = "Atualiza os dados do enfermeiro especificado pelo ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Enfermeiro atualizado",
                    content = @Content(schema = @Schema(implementation = NurseResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Enfermeiro não encontrado",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            )
    })
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
