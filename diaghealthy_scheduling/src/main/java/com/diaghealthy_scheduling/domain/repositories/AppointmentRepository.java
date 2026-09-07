package com.diaghealthy_scheduling.domain.repositories;

import com.diaghealthy_scheduling.domain.entities.Appointment;

import java.util.Optional;
import java.util.UUID;

public interface AppointmentRepository {
    Appointment saveAppointment (Appointment appointment);
    Optional <Appointment> findAppointmentById (UUID id);
    Appointment updateAppointment (Appointment appointment);
}
