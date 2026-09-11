package com.diaghealthy_notification.infrastructure.controllers;

import com.diaghealthy_notification.application.exceptions.UnauthorizedException;
import com.diaghealthy_notification.application.inputs.NotificationCreateInput;
import com.diaghealthy_notification.application.services.CurrentUser;
import com.diaghealthy_notification.application.usecases.CreateNotificationUseCase;
import com.diaghealthy_notification.application.usecases.FindNotificationByIdUseCase;
import com.diaghealthy_notification.application.usecases.FindNotificationsByPatientUseCase;
import com.diaghealthy_notification.domain.entities.Notification;
import com.diaghealthy_notification.domain.enuns.Role;
import com.diaghealthy_notification.infrastructure.dtos.notification.NotificationCreateDTO;
import com.diaghealthy_notification.infrastructure.dtos.notification.NotificationResponseDTO;
import com.diaghealthy_notification.infrastructure.mappers.NotificationMapper;
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
@RequestMapping("api/v1/notifications")
@Tag(name = "Notificações", description = "Envio e consulta de notificações de lembrete aos pacientes")
public class NotificationController {

    private final CreateNotificationUseCase createNotificationUseCase;
    private final FindNotificationByIdUseCase findNotificationByIdUseCase;
    private final FindNotificationsByPatientUseCase findNotificationsByPatientUseCase;
    private final NotificationMapper notificationMapper;
    private final CurrentUser currentUser;

    public NotificationController(
            CreateNotificationUseCase createNotificationUseCase,
            FindNotificationByIdUseCase findNotificationByIdUseCase,
            FindNotificationsByPatientUseCase findNotificationsByPatientUseCase,
            NotificationMapper notificationMapper,
            CurrentUser currentUser
    ) {
        this.createNotificationUseCase = createNotificationUseCase;
        this.findNotificationByIdUseCase = findNotificationByIdUseCase;
        this.findNotificationsByPatientUseCase = findNotificationsByPatientUseCase;
        this.notificationMapper = notificationMapper;
        this.currentUser = currentUser;
    }

    @Operation(
            summary = "Registrar notificação",
            description = "Cria uma notificação de lembrete para um paciente sobre uma consulta e simula o envio"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Notificação criada e enviada",
                    content = @Content(schema = @Schema(implementation = NotificationResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            )
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE')")
    public ResponseEntity<NotificationResponseDTO> create(
            @RequestBody NotificationCreateDTO notificationCreateDTO
    ) {

        NotificationCreateInput input =
                notificationMapper.toCreateInput(notificationCreateDTO);

        Notification notification =
                createNotificationUseCase.execute(input);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(notificationMapper.toDto(notification));
    }

    @Operation(
            summary = "Buscar notificação por ID",
            description = "Busca os dados de uma notificação especificada pelo ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Notificação encontrada",
                    content = @Content(schema = @Schema(implementation = NotificationResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso negado",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Notificação não encontrada",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            )
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE', 'PATIENT')")
    public ResponseEntity<NotificationResponseDTO> findById(
            @PathVariable UUID id
    ) {

        Notification notification = findNotificationByIdUseCase.execute(id);

        assertOwnership(notification.getPatientId());

        return ResponseEntity.ok(notificationMapper.toDto(notification));
    }

    @Operation(
            summary = "Listar notificações do paciente",
            description = "Lista todas as notificações enviadas para o paciente especificado"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Notificações encontradas",
                    content = @Content(schema = @Schema(implementation = NotificationResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso negado",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            )
    })
    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE', 'PATIENT')")
    public ResponseEntity<List<NotificationResponseDTO>> findByPatient(
            @PathVariable UUID patientId
    ) {

        assertOwnership(patientId);

        List<NotificationResponseDTO> notifications = findNotificationsByPatientUseCase
                .execute(patientId)
                .stream()
                .map(notificationMapper::toDto)
                .toList();

        return ResponseEntity.ok(notifications);
    }

    private void assertOwnership(UUID patientId) {
        if (currentUser.getRole() == Role.PATIENT && !currentUser.getId().equals(patientId)) {
            throw new UnauthorizedException("Você só pode visualizar as suas próprias notificações");
        }
    }
}
