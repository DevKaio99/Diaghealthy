package com.diaghealthy_scheduling.infrastructure.circuitbreaker;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class CircuitBreakerConfiguration {

    @Value("${circuit-breaker.failure-rate-threshold:50}")
    private float failureRateThreshold;

    @Value("${circuit-breaker.wait-duration-in-open-state-seconds:10}")
    private long waitDurationInOpenStateSeconds;

    @Value("${circuit-breaker.sliding-window-size:10}")
    private int slidingWindowSize;

    @Value("${circuit-breaker.permitted-calls-in-half-open-state:3}")
    private int permittedCallsInHalfOpenState;

    @Bean
    public CircuitBreaker userServiceCircuitBreaker() {

        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(failureRateThreshold)
                .waitDurationInOpenState(Duration.ofSeconds(waitDurationInOpenStateSeconds))
                .slidingWindowSize(slidingWindowSize)
                .minimumNumberOfCalls(slidingWindowSize)
                .permittedNumberOfCallsInHalfOpenState(permittedCallsInHalfOpenState)
                .build();

        return CircuitBreaker.of("userService", config);
    }
}
