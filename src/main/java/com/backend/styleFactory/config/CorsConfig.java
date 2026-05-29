package com.backend.styleFactory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Configuración global de CORS.
 * Permite solicitudes desde el frontend de desarrollo y desde GitHub Pages.
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // Orígenes del frontend (GitHub Pages, Live Server, Vite, etc.)
        config.addAllowedOriginPattern("https://enithv.github.io");
        config.addAllowedOriginPattern("http://localhost:*");
        config.addAllowedOriginPattern("http://127.0.0.1:*");
        config.addAllowedOriginPattern("https://localhost:*");
        config.addAllowedOriginPattern("https://127.0.0.1:*");

        // Métodos HTTP que puede utilizar el frontend
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("OPTIONS");

        // Encabezados permitidos (incluye Authorization para JWT)
        config.addAllowedHeader("*");

        // Con JWT no se necesitan credenciales (cookies), por lo que se mantiene false
        config.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}