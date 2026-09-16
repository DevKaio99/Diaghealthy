package com.diaghealthy_scheduling.infrastructure.clients;

import com.diaghealthy_scheduling.application.gateways.UserResponse;
import com.diaghealthy_scheduling.application.gateways.UserServiceGateway;
import com.diaghealthy_scheduling.domain.enuns.Role;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
public class UserServiceClient implements UserServiceGateway {

    private final RestClient restClient;
    private final CircuitBreaker circuitBreaker;

    public UserServiceClient(RestClient restClient, CircuitBreaker userServiceCircuitBreaker) {
        this.restClient = restClient;
        this.circuitBreaker = userServiceCircuitBreaker;
    }

    @Override
    public UserResponse findUserById(UUID id) {

        return circuitBreaker.executeSupplier(() -> {

            UserResponseDTO response = restClient
                    .get()
                    .uri("/api/v1/user/{id}", id)
                    .retrieve()
                    .body(UserResponseDTO.class);

            return new UserResponse(
                    response.id(),
                    response.email(),
                    response.role(),
                    response.active()
            );
        });
    }

    @Override
    public UserResponse findPatientById(UUID id) {

        UserResponse user = findUserById(id);

        if (user.role() != Role.PATIENT) {
            throw new IllegalArgumentException("Usuário com o Id " + id + " não é um paciente");
        }

        return user;
    }

    @Override
    public UserResponse findDoctorById(UUID id) {

        UserResponse user = findUserById(id);

        if (user.role() != Role.DOCTOR) {
            throw new IllegalArgumentException("Usuário com o Id " + id + " não é um doutor");
        }

        return user;
    }

    @Override
    public UserResponse findNurseById(UUID id) {

        UserResponse user = findUserById(id);

        if (user.role() != Role.NURSE) {
            throw new IllegalArgumentException("Usuário com o Id " + id + " não é um enfermeiro");
        }

        return user;
    }
}