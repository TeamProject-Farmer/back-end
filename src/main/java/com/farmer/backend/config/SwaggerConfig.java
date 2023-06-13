package com.farmer.backend.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi jwtApi() {
        return GroupedOpenApi.builder()
                .group("jwt-api")
                .pathsToMatch("/**")
                .build();
    }
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("farmer Project API Document")
                .version("v0.0.1")
                .description("파머 프로젝트의 API 명세서입니다.");
        return new OpenAPI()
                .components(new Components())
                .info(info);
    }


}
