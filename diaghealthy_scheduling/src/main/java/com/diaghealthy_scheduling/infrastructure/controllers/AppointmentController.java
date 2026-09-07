package com.diaghealthy_scheduling.infrastructure.controllers;

import com.diaghealthy_scheduling.application.inputs.AppointmentCreateInput;
import com.diaghealthy_scheduling.application.inputs.AppointmentUpdateInput;
import com.diaghealthy_scheduling.application.usecases.CreateAppointmentUseCase;
import com.diaghealthy_scheduling.application.usecases.UpdateAppointmentUseCase;
import com.diaghealthy_scheduling.domain.entities.Appointment;
import com.diaghealthy_scheduling.infrastructure.dtos.appointment.AppointmentCreateDTO;
import com.diaghealthy_scheduling.infrastructure.dtos.appointment.AppointmentResponseDTO;
import com.diaghealthy_scheduling.infrastructure.dtos.appointment.AppointmentUpdateDTO;
import com.diaghealthy_scheduling.infrastructure.mappers.AppointmentMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/appointments")
public class AppointmentController {

    private final CreateAppointmentUseCase createAppointmentUseCase;
    private final UpdateAppointmentUseCase updateAppointmentUseCase;
    private final AppointmentMapper appointmentMapper;

    public AppointmentController(
            CreateAppointmentUseCase createAppointmentUseCase,
            UpdateAppointmentUseCase updateAppointmentUseCase,
            AppointmentMapper appointmentMapper
    ) {
        this.createAppointmentUseCase = createAppointmentUseCase;
        this.updateAppointmentUseCase = updateAppointmentUseCase;
        this.appointmentMapper = appointmentMapper;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE')")
    public ResponseEntity<AppointmentResponseDTO> create(
            @RequestBody AppointmentCreateDTO appointmentCreateDTO
    ) {

        AppointmentCreateInput input =
                appointmentMapper.toCreateInput(appointmentCreateDTO);

        Appointment appointment =
                createAppointmentUseCase.execute(input);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(appointmentMapper.toDto(appointment));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE')")
    public ResponseEntity<AppointmentResponseDTO> update(
            @PathVariable UUID id,
            @RequestBody AppointmentUpdateDTO appointmentUpdateDTO
    ) {

        AppointmentUpdateInput input = appointmentMapper.toUpdateInput(appointmentUpdateDTO);
        Appointment appointment = updateAppointmentUseCase.execute(id, input);

        return ResponseEntity.ok(appointmentMapper.toDto(appointment)
        );
    }
}