package com.backend.styleFactory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Configuración global de CORS.
 * Permite solicitudes desde el frontend en GitHub Pages y entornos locales.
 *
 * <p>Frontend publicado: {@code https://enithv.github.io/stylefactory/}
 * (repositorio {@code EnithV/stylefactory}). El encabezado {@code Origin} de GitHub Pages
 * es {@code https://enithv.github.io}, sin la ruta del proyecto.</p>
 */
@Configuration
public class CorsConfig {

    /** Origen del sitio en GitHub Pages (repo stylefactory). */
    public static final String FRONTEND_GITHUB_PAGES_ORIGIN = "https://enithv.github.io";

    /** URL pública del frontend (documentación / pruebas manuales). */
    public static final String FRONTEND_PUBLIC_URL = "https://enithv.github.io/stylefactory/";

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // GitHub Pages (stylefactory y cualquier otro proyecto bajo enithv.github.io)
        config.addAllowedOriginPattern(FRONTEND_GITHUB_PAGES_ORIGIN);
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