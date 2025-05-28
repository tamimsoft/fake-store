package com.tamimSoft.store.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customSwaggerConfig() {
        return new OpenAPI()
                .info(new Info()
                        .title("Store API")
                        .description("Created by Tamim Hasan")
                        .contact(new Contact().name("Tamim Hasan").email("tamim@example.com"))
                        .summary("a product ot tamimSoft")
                        .version("1.0")
                )
                .servers(List.of(
                        new Server().url("https://fake-store-xq1t.onrender.com").description("Production on Render"),
                        new Server().url("https://fake-store-production.up.railway.app").description("Production Railway"),
                        new Server().url("http://localhost:8080").description("Local Server")
                ))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .in(SecurityScheme.In.HEADER)
                                        .name("Authorization")
                        )
                )
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth")); // Apply security globally
    }
}

