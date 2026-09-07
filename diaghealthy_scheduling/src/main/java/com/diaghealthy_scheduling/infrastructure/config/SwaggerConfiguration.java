package com.diaghealthy_scheduling.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI customizarOpenAPI () {
        return new OpenAPI().info(new Info()
                .title("diaghealthy scheduling")
                .version("1.0.0")
                .description("Documentação do diaghealthy scheduling API"));
    }
}