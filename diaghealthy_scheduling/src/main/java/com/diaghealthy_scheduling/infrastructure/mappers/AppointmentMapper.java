package com.diaghealthy_scheduling.infrastructure.mappers;

import com.diaghealthy_scheduling.application.inputs.AppointmentCreateInput;
import com.diaghealthy_scheduling.application.inputs.AppointmentUpdateInput;
import com.diaghealthy_scheduling.domain.entities.Appointment;
import com.diaghealthy_scheduling.infrastructure.dtos.appointment.AppointmentCreateDTO;
import com.diaghealthy_scheduling.infrastructure.dtos.appointment.AppointmentResponseDTO;
import com.diaghealthy_scheduling.infrastructure.dtos.appointment.AppointmentUpdateDTO;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {

    public AppointmentCreateInput toCreateInput(AppointmentCreateDTO appointmentCreateDTO) {
        return new AppointmentCreateInput(
                appointmentCreateDTO.patientId(),
                appointmentCreateDTO.doctorId(),
                appointmentCreateDTO.nurseId(),
                appointmentCreateDTO.scheduledAt(),
                appointmentCreateDTO.reason()
        );
    }

    public AppointmentResponseDTO toDto(Appointment appointment) {
        return new AppointmentResponseDTO(
                appointment.getId(),
                appointment.getPatientId(),
                appointment.getDoctorId(),
                appointment.getNurseId(),
                appointment.getScheduledAt(),
                appointment.getStatus(),
                appointment.getReason(),
                appointment.getCreatedAt(),
                appointment.getUpdatedAt()
        );
    }

    public AppointmentUpdateInput toUpdateInput(AppointmentUpdateDTO appointmentUpdateDTO) {
        return new AppointmentUpdateInput(
                appointmentUpdateDTO.scheduledAt(),
                appointmentUpdateDTO.status(),
                appointmentUpdateDTO.reason()
        );
    }
}