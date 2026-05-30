package com.backend.styleFactory.config;

import com.backend.styleFactory.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuración de seguridad de la aplicación.
 * Swagger/OpenAPI va en una cadena aparte, sin JWT, para evitar 403 en /v3/api-docs.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  /** Rutas de documentación (springdoc + Swagger UI). */
  private static final String[] SWAGGER_PATHS = {
      "/swagger-ui/**",
      "/swagger-ui.html",
      "/v3/api-docs",
      "/v3/api-docs/**",
      "/webjars/**"
  };

  private final JwtFilter jwtFilter;

  public SecurityConfig(JwtFilter jwtFilter) {
    this.jwtFilter = jwtFilter;
  }

  /**
   * Solo documentación: acceso público, sin filtro JWT.
   */
  @Bean
  @Order(1)
  public SecurityFilterChain swaggerSecurityFilterChain(HttpSecurity http) throws Exception {
    http.securityMatcher(SWAGGER_PATHS)
        .csrf(csrf -> csrf.disable())
        .cors(Customizer.withDefaults())
        .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
    return http.build();
  }

  /**
   * API REST: auth, roles y JWT.
   */
  @Bean
  @Order(2)
  public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
    http.cors(Customizer.withDefaults())
        .csrf(csrf -> csrf.disable())
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers(HttpMethod.OPTIONS, "/**")
                    .permitAll()
                    .requestMatchers("/", "/health")
                    .permitAll()
                    .requestMatchers("/auth/**")
                    .permitAll()
                    .requestMatchers("/admin/**")
                    .hasRole("ADMIN")
                    .requestMatchers("/empleados/**")
                    .hasAnyRole("ADMIN", "EMPLEADO")
                    .anyRequest()
                    .authenticated())
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
