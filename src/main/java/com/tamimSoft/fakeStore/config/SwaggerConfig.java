package com.tamimSoft.fakeStore.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customSwaggerConfig() {
        return new OpenAPI()
                .info(
                        new Info().title("Fake Store API").description("Created by Tamim Hasan")
                )
                .servers(
                        List.of(
                                new Server().url("http://localhost:8080"),
                                new Server().url("https://insightful-eagerness-production.up.railway.app")
                        )
                );
    }
}
