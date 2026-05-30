package com.backend.styleFactory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
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
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", crearConfiguracionCors());
        return source;
    }

    @Bean
    public CorsFilter corsFilter(CorsConfigurationSource corsConfigurationSource) {
        return new CorsFilter(corsConfigurationSource);
    }

    private static CorsConfiguration crearConfiguracionCors() {
        CorsConfiguration config = new CorsConfiguration();

        config.addAllowedOriginPattern(FRONTEND_GITHUB_PAGES_ORIGIN);
        config.addAllowedOriginPattern("http://localhost:*");
        config.addAllowedOriginPattern("http://127.0.0.1:*");
        config.addAllowedOriginPattern("https://localhost:*");
        config.addAllowedOriginPattern("https://127.0.0.1:*");

        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("PATCH");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("OPTIONS");

        config.addAllowedHeader("*");

        config.setAllowCredentials(false);

        return config;
    }
}
