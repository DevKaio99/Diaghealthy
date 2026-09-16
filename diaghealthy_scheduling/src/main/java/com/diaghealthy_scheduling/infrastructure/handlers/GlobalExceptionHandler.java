package com.diaghealthy_scheduling.infrastructure.handlers;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(CallNotPermittedException.class)
    public ProblemDetail handlerCallNotPermitted(
            CallNotPermittedException ex,
            HttpServletRequest request) {

        log.warn("Circuito '{}' aberto — chamada bloqueada em {}", ex.getCausingCircuitBreakerName(), request.getRequestURI());

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.SERVICE_UNAVAILABLE);

        problem.setTitle("Serviço temporariamente indisponível");
        problem.setDetail("O serviço de usuários está indisponível no momento. Tente novamente em instantes.");
        problem.setProperty("timestamp", LocalDateTime.now());
        problem.setProperty("path", request.getRequestURI());

        return problem;
    }
}
