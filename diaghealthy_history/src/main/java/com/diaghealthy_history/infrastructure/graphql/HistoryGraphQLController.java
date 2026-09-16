package com.diaghealthy_history.infrastructure.graphql;

import com.diaghealthy_history.application.exceptions.UnauthorizedException;
import com.diaghealthy_history.application.services.CurrentUser;
import com.diaghealthy_history.application.usecases.FindHistoryByIdUseCase;
import com.diaghealthy_history.application.usecases.FindHistoryByPatientUseCase;
import com.diaghealthy_history.domain.entities.MedicalRecord;
import com.diaghealthy_history.domain.enuns.Role;
import com.diaghealthy_history.infrastructure.dtos.history.HistoryResponseDTO;
import com.diaghealthy_history.infrastructure.mappers.HistoryMapper;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class HistoryGraphQLController {

    private final FindHistoryByIdUseCase findHistoryByIdUseCase;
    private final FindHistoryByPatientUseCase findHistoryByPatientUseCase;
    private final HistoryMapper historyMapper;
    private final CurrentUser currentUser;

    public HistoryGraphQLController(
            FindHistoryByIdUseCase findHistoryByIdUseCase,
            FindHistoryByPatientUseCase findHistoryByPatientUseCase,
            HistoryMapper historyMapper,
            CurrentUser currentUser
    ) {
        this.findHistoryByIdUseCase = findHistoryByIdUseCase;
        this.findHistoryByPatientUseCase = findHistoryByPatientUseCase;
        this.historyMapper = historyMapper;
        this.currentUser = currentUser;
    }

    @QueryMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE', 'PATIENT')")
    public HistoryResponseDTO medicalRecord(@Argument UUID id) {

        MedicalRecord record = findHistoryByIdUseCase.execute(id);

        assertOwnership(record.getPatientId());

        return historyMapper.toDto(record);
    }

    @QueryMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE', 'PATIENT')")
    public List<HistoryResponseDTO> medicalRecordsByPatient(
            @Argument UUID patientId,
            @Argument Boolean onlyFuture
    ) {

        assertOwnership(patientId);

        boolean future = Boolean.TRUE.equals(onlyFuture);

        return findHistoryByPatientUseCase.execute(patientId, future)
                .stream()
                .map(historyMapper::toDto)
                .toList();
    }

    private void assertOwnership(UUID patientId) {
        if (currentUser.getRole() == Role.PATIENT && !currentUser.getId().equals(patientId)) {
            throw new UnauthorizedException("Você só pode visualizar o seu próprio histórico");
        }
    }
}
