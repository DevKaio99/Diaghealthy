package com.diaghealthy_history.infrastructure.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Value("${user-service.url}")
    private String userServiceUrl;

    @Bean
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }

    @Bean
    public RestClient restClient(
            RestClient.Builder builder,
            HttpServletRequest request
    ) {
        return builder
                .baseUrl(userServiceUrl)
                .requestInterceptor((req, body, execution) -> {

                    String authorization =
                            request.getHeader("Authorization");

                    if (authorization != null) {
                        req.getHeaders().set(
                                "Authorization",
                                authorization
                        );
                    }

                    return execution.execute(req, body);
                })
                .build();
    }
}
