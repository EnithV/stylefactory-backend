package com.backend.styleFactory.DTO;

import com.backend.styleFactory.model.RolUsuario;

public class UsuarioResponseDTO {

    private Long id;
    private String nombre;
    private String correo;
    private String telefono;
    private RolUsuario rol;
    private boolean estado;

    public UsuarioResponseDTO() {
    }

    public UsuarioResponseDTO(Long id, String nombre, String correo, String telefono, RolUsuario rol, boolean estado) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.rol = rol;
        this.estado = estado;
    }

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
}
