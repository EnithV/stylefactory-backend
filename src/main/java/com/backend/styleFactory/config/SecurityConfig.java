package com.backend.styleFactory.config;

import com.backend.styleFactory.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuración de seguridad de la aplicación.
 * Define las reglas de acceso a las rutas y la integración del filtro JWT.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String[] SWAGGER_PATHS = {
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/webjars/**"
    };

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    /**
     * Cadena de filtros de seguridad que establece las políticas de sesión,
     * protección CSRF, autorización de rutas y agrega el filtro JWT.
     *
     * @param http Objeto HttpSecurity proporcionado por Spring
     * @return SecurityFilterChain configurado
     * @throws Exception si ocurre un error en la configuración
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Endpoints públicos
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers(SWAGGER_PATHS).permitAll()
                        // Endpoints restringidos por rol
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/empleados/**").hasAnyRole("ADMIN", "EMPLEADO")
                        // Cualquier otra petición requiere autenticación
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}