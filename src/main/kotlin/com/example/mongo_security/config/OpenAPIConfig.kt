package com.example.mongo_security.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.security.SecurityScheme.In.HEADER
import io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders.AUTHORIZATION

@Configuration
class OpenAPIConfig {
    companion object {
        const val BEARER_KEY = "bearer-key"
    }

    @Bean
    fun customOpenAPI(): OpenAPI = OpenAPI()
        .components(
            Components().addSecuritySchemes(
                BEARER_KEY,
                SecurityScheme()
                    .type(HTTP)
                    .scheme("bearer")
                    .`in`(HEADER)
                    .name(AUTHORIZATION)
                    .bearerFormat("JWT")
            )
        )
}
