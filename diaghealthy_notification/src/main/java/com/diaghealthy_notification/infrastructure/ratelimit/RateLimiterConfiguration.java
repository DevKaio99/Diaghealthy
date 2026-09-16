package com.diaghealthy_notification.infrastructure.ratelimit;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RateLimiterConfiguration {

    @Value("${rate-limiter.limit-for-period:50}")
    private int limitForPeriod;

    @Value("${rate-limiter.limit-refresh-period-ms:1000}")
    private long limitRefreshPeriodMs;

    @Value("${rate-limiter.timeout-duration-ms:0}")
    private long timeoutDurationMs;

    @Bean
    public RateLimiter apiRateLimiter() {

        RateLimiterConfig config = RateLimiterConfig.custom()
                .limitForPeriod(limitForPeriod)
                .limitRefreshPeriod(Duration.ofMillis(limitRefreshPeriodMs))
                .timeoutDuration(Duration.ofMillis(timeoutDurationMs))
                .build();

        return RateLimiter.of("api", config);
    }
}
