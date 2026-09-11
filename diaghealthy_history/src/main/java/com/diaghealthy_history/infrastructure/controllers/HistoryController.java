package com.diaghealthy_history.infrastructure.controllers;

import com.diaghealthy_history.application.exceptions.UnauthorizedException;
import com.diaghealthy_history.application.inputs.RegisterHistoryInput;
import com.diaghealthy_history.application.inputs.UpdateHistoryInput;
import com.diaghealthy_history.application.services.CurrentUser;
import com.diaghealthy_history.application.usecases.FindHistoryByIdUseCase;
import com.diaghealthy_history.application.usecases.FindHistoryByPatientUseCase;
import com.diaghealthy_history.application.usecases.RegisterHistoryUseCase;
import com.diaghealthy_history.application.usecases.UpdateHistoryUseCase;
import com.diaghealthy_history.domain.entities.MedicalRecord;
import com.diaghealthy_history.domain.enuns.Role;
import com.diaghealthy_history.infrastructure.dtos.history.HistoryRegisterDTO;
import com.diaghealthy_history.infrastructure.dtos.history.HistoryResponseDTO;
import com.diaghealthy_history.infrastructure.dtos.history.HistoryUpdateDTO;
import com.diaghealthy_history.infrastructure.mappers.HistoryMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/history")
@Tag(name = "Histórico", description = "Registro e consulta do histórico de consultas")
public class HistoryController {

    private final RegisterHistoryUseCase registerHistoryUseCase;
    private final UpdateHistoryUseCase updateHistoryUseCase;
    private final FindHistoryByIdUseCase findHistoryByIdUseCase;
    private final FindHistoryByPatientUseCase findHistoryByPatientUseCase;
    private final HistoryMapper historyMapper;
    private final CurrentUser currentUser;

    public HistoryController(
            RegisterHistoryUseCase registerHistoryUseCase,
            UpdateHistoryUseCase updateHistoryUseCase,
            FindHistoryByIdUseCase findHistoryByIdUseCase,
            FindHistoryByPatientUseCase findHistoryByPatientUseCase,
            HistoryMapper historyMapper,
            CurrentUser currentUser
    ) {
        this.registerHistoryUseCase = registerHistoryUseCase;
        this.updateHistoryUseCase = updateHistoryUseCase;
        this.findHistoryByIdUseCase = findHistoryByIdUseCase;
        this.findHistoryByPatientUseCase = findHistoryByPatientUseCase;
        this.historyMapper = historyMapper;
        this.currentUser = currentUser;
    }

    @Operation(
            summary = "Registrar histórico",
            description = "Registra um novo registro de histórico de consulta"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Registro de histórico criado",
                    content = @Content(schema = @Schema(implementation = HistoryResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            )
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE')")
    public ResponseEntity<HistoryResponseDTO> register(
            @RequestBody HistoryRegisterDTO historyRegisterDTO
    ) {

        RegisterHistoryInput input =
                historyMapper.toRegisterInput(historyRegisterDTO);

        MedicalRecord record =
                registerHistoryUseCase.execute(input);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(historyMapper.toDto(record));
    }

    @Operation(
            summary = "Atualizar histórico",
            description = "Atualiza o status e/ou as observações de um registro de histórico"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Registro de histórico atualizado",
                    content = @Content(schema = @Schema(implementation = HistoryResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Registro de histórico não encontrado",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            )
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE')")
    public ResponseEntity<HistoryResponseDTO> update(
            @PathVariable UUID id,
            @RequestBody HistoryUpdateDTO historyUpdateDTO
    ) {

        UpdateHistoryInput input = historyMapper.toUpdateInput(historyUpdateDTO);
        MedicalRecord record = updateHistoryUseCase.execute(id, input);

        return ResponseEntity.ok(historyMapper.toDto(record));
    }

    @Operation(
            summary = "Buscar histórico por ID",
            description = "Busca um registro de histórico especificado pelo ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Registro de histórico encontrado",
                    content = @Content(schema = @Schema(implementation = HistoryResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso negado",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Registro de histórico não encontrado",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            )
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE', 'PATIENT')")
    public ResponseEntity<HistoryResponseDTO> findById(
            @PathVariable UUID id
    ) {

        MedicalRecord record = findHistoryByIdUseCase.execute(id);

        assertOwnership(record.getPatientId());

        return ResponseEntity.ok(historyMapper.toDto(record));
    }

    @Operation(
            summary = "Listar histórico do paciente",
            description = "Lista os registros de histórico do paciente especificado, com opção de filtrar apenas as consultas futuras"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Registros de histórico encontrados",
                    content = @Content(schema = @Schema(implementation = HistoryResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso negado",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            )
    })
    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE', 'PATIENT')")
    public ResponseEntity<List<HistoryResponseDTO>> findByPatient(
            @PathVariable UUID patientId,
            @RequestParam(defaultValue = "false") boolean onlyFuture
    ) {

        assertOwnership(patientId);

        List<HistoryResponseDTO> records = findHistoryByPatientUseCase
                .execute(patientId, onlyFuture)
                .stream()
                .map(historyMapper::toDto)
                .toList();

        return ResponseEntity.ok(records);
    }

    private void assertOwnership(UUID patientId) {
        if (currentUser.getRole() == Role.PATIENT && !currentUser.getId().equals(patientId)) {
            throw new UnauthorizedException("Você só pode visualizar o seu próprio histórico");
        }
    }
}
