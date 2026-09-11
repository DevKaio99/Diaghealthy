package com.diaghealthy_history.infrastructure.mappers;

import com.diaghealthy_history.application.inputs.RegisterHistoryInput;
import com.diaghealthy_history.application.inputs.UpdateHistoryInput;
import com.diaghealthy_history.domain.entities.MedicalRecord;
import com.diaghealthy_history.infrastructure.dtos.history.HistoryRegisterDTO;
import com.diaghealthy_history.infrastructure.dtos.history.HistoryResponseDTO;
import com.diaghealthy_history.infrastructure.dtos.history.HistoryUpdateDTO;
import org.springframework.stereotype.Component;

@Component
public class HistoryMapper {

    public RegisterHistoryInput toRegisterInput(HistoryRegisterDTO historyRegisterDTO) {
        return new RegisterHistoryInput(
                historyRegisterDTO.appointmentId(),
                historyRegisterDTO.patientId(),
                historyRegisterDTO.doctorId(),
                historyRegisterDTO.nurseId(),
                historyRegisterDTO.scheduledAt(),
                historyRegisterDTO.status(),
                historyRegisterDTO.reason()
        );
    }

    public UpdateHistoryInput toUpdateInput(HistoryUpdateDTO historyUpdateDTO) {
        return new UpdateHistoryInput(
                historyUpdateDTO.status(),
                historyUpdateDTO.notes()
        );
    }

    public HistoryResponseDTO toDto(MedicalRecord record) {
        return new HistoryResponseDTO(
                record.getId(),
                record.getAppointmentId(),
                record.getPatientId(),
                record.getDoctorId(),
                record.getNurseId(),
                record.getScheduledAt(),
                record.getStatus(),
                record.getReason(),
                record.getNotes(),
                record.getCreatedAt(),
                record.getUpdatedAt()
        );
    }
}
