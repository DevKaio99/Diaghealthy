package com.diaghealthy_notification.application.gateways;

import java.util.UUID;

public interface UserServiceGateway {
    UserResponse findUserById(UUID id);
    UserResponse findPatientById(UUID id);
}
