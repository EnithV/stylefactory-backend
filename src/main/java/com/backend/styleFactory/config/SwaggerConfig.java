package com.backend.styleFactory.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración global de OpenAPI / Swagger.
 * Define la información general de la API que se muestra en la UI de Swagger.
 */
@Configuration
public class SwaggerConfig {

    /**
     * Bean que personaliza el documento OpenAPI con el título, versión y descripción.
     *
     * @return OpenAPI con la información de la aplicación
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Style Factory API")
                        .version("1.0")
                        .description("Documentación de la API REST de Style Factory. " +
                                "Gestiona usuarios, empleados, servicios, horarios y reservas."));
    }
}