package com.backend.styleFactory.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Entidad que representa un usuario del sistema.
 * Implementa {@link UserDetails} para integrarse con Spring Security.
 */
@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @Column(nullable = false, length = 200)
    private String nombre;

    @Column(nullable = false, length = 200, unique = true)
    private String correo;

    @Column(nullable = false, length = 20)
    private String telefono;

    @Column(nullable = false, length = 200)
    private String contrasena;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RolUsuario rol;

    @Column(nullable = false)
    private boolean estado = true;

    public Usuario() {
    }

    public Usuario(String nombre, String correo, String telefono, String contrasena, RolUsuario rol, boolean estado) {
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.contrasena = contrasena;
        this.rol = rol;
        this.estado = estado;
    }

    // ─────────────────────────────────────────────
    // Getters y setters de la entidad
    // ─────────────────────────────────────────────

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public RolUsuario getRol() {
        return rol;
    }

    public void setRol(RolUsuario rol) {
        this.rol = rol;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    // ─────────────────────────────────────────────
    // Implementación de UserDetails
    // ─────────────────────────────────────────────

    /**
     * Devuelve los roles del usuario como una lista de authorities.
     * Spring Security antepone automáticamente "ROLE_" al nombre del rol.
     *
     * @return Colección de autoridades (roles) del usuario
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + rol.name()));
    }

    /**
     * Retorna la contraseña almacenada para que Spring Security la valide.
     *
     * @return Contraseña codificada del usuario
     */
    @Override
    public String getPassword() {
        return contrasena;
    }

    /**
     * Retorna el identificador único del usuario dentro de Spring Security.
     * Se utiliza el correo como nombre de usuario.
     *
     * @return Correo del usuario
     */
    @Override
    public String getUsername() {
        return correo;
    }

    /**
     * Indica que la cuenta no expira.
     *
     * @return true (no expira)
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indica si la cuenta está bloqueada.
     * La cuenta se bloquea cuando el campo {@code estado} es false.
     *
     * @return false si está bloqueada, true en caso contrario
     */
    @Override
    public boolean isAccountNonLocked() {
        return estado;
    }

    /**
     * Indica que las credenciales no expiran.
     *
     * @return true (no expiran)
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indica si el usuario está habilitado.
     * Coincide con el campo {@code estado}.
     *
     * @return true si está habilitado
     */
    @Override
    public boolean isEnabled() {
        return estado;
    }
}