package com.example.climate_backend.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

// http://localhost:8080/swagger-ui/index.html
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(info())
                .servers(servers());
    }

    private Info info() {
        return new Info()
                .title("멍로깅 API")
                .description("2025 트렌디톤 기후감수성 1팀")
                .version("1.0");
    }

    private List<Server> servers() {
        return List.of(new Server()
                .url("http://3.34.183.9:8080")
                .description("Configured Server"));
    }

}
