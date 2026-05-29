package com.backend.styleFactory.controller;

import com.backend.styleFactory.config.CorsConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Endpoint público de comprobación. El backend es una API REST, no una página web.
 */
@RestController
public class HealthController {

    @GetMapping("/")
    public Map<String, Object> inicio() {
        return Map.of(
                "estado", "ok",
                "servicio", "Style Factory API",
                "mensaje", "API en línea. Use los endpoints REST o la documentación Swagger.",
                "frontend", CorsConfig.FRONTEND_PUBLIC_URL,
                "documentacion", "/swagger-ui/index.html",
                "auth", Map.of(
                        "registro", "POST /auth/register",
                        "login", "POST /auth/login"
                )
        );
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("estado", "ok");
    }
}
