package com.diaghealthy_history.application.gateways;

import java.util.UUID;

public interface UserServiceGateway {
    UserResponse findUserById(UUID id);
    UserResponse findPatientById(UUID id);
    UserResponse findDoctorById(UUID id);
    UserResponse findNurseById(UUID id);
}
