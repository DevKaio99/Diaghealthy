package com.diaghealthy_scheduling.application.gateways;

import com.diaghealthy_scheduling.domain.entities.Appointment;

public interface AppointmentEventGateway {

    void publishAppointmentCreated(Appointment appointment);
}

