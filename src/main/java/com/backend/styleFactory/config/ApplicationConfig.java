package com.backend.styleFactory.config;

import com.backend.styleFactory.repository.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Clase de configuración que define los beans fundamentales
 * para el flujo de autenticación en Spring Security.
 */
@Configuration
public class ApplicationConfig {

    private final UsuarioRepository usuarioRepository;

    public ApplicationConfig(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Servicio de carga de usuarios desde la base de datos.
     * Devuelve la entidad {@link com.backend.styleFactory.model.Usuario},
     * que implementa {@link org.springframework.security.core.userdetails.UserDetails}.
     *
     * @return Implementación de UserDetailsService
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> usuarioRepository
                .findByCorreo(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Usuario no encontrado con correo: " + username));
    }

    /**
     * Proveedor de autenticación que utiliza el UserDetailsService
     * y el codificador BCrypt para verificar las credenciales.
     *
     * @return DaoAuthenticationProvider configurado
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * Expone el AuthenticationManager global de Spring Security.
     *
     * @param config Configuración de autenticación inyectada
     * @return AuthenticationManager
     * @throws Exception si ocurre un error al crearlo
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Codificador de contraseñas con algoritmo BCrypt.
     *
     * @return BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}