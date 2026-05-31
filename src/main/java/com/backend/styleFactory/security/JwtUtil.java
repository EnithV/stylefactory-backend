package com.backend.styleFactory.security;

import com.backend.styleFactory.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Componente de utilidad para operaciones con tokens JWT.
 * Proporciona métodos para generar, validar y extraer información
 * de tokens de acceso.
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expirationMs;

    /**
     * Obtiene la clave de firma a partir de jwt.secret (properties o env).
     *
     * @return SecretKey para HMAC-SHA256
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * Genera un token JWT firmado que incluye el correo como subject
     * y claims adicionales con el rol y el nombre del usuario.
     *
     * @param usuario Entidad que representa al usuario autenticado
     * @return Token JWT compacto
     */
    public String generateToken(Usuario usuario) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("rol", usuario.getRol().name());
        claims.put("nombre", usuario.getNombre());

        return Jwts.builder()
                .claims(claims)
                .subject(usuario.getCorreo())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Extrae el cuerpo (claims) de un token JWT verificando su firma.
     *
     * @param token Token JWT
     * @return Claims contenidos en el token
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Obtiene el nombre de usuario (correo) almacenado en el subject del token.
     *
     * @param token Token JWT
     * @return Correo del usuario
     */
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    /**
     * Verifica la validez del token comprobando su firma y fecha de expiración.
     *
     * @param token Token JWT a validar
     * @return true si el token es válido, false en caso contrario
     */
    public boolean isTokenValid(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}