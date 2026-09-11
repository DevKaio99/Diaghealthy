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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/notifications")
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

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE', 'PATIENT')")
    public ResponseEntity<NotificationResponseDTO> findById(
            @PathVariable UUID id
    ) {

        Notification notification = findNotificationByIdUseCase.execute(id);

        assertOwnership(notification.getPatientId());

        return ResponseEntity.ok(notificationMapper.toDto(notification));
    }

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
