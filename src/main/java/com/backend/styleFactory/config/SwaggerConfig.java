package com.backend.styleFactory.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración OpenAPI / Swagger UI.
 * Registra el esquema JWT Bearer para el botón Authorize y la metadata de la API.
 */
@Configuration
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Style Factory API")
                        .version("1.0")
                        .description("Documentación de la API REST de Style Factory. " +
                                "Gestiona usuarios, empleados, servicios, horarios y reservas. " +
                                "Use POST /auth/login y luego Authorize con el token JWT."))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }
}
