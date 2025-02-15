package com.example.climate_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ClimateBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClimateBackendApplication.class, args);
    }

}
