package com.backend.styleFactory.auth;

/**
 * DTO que recibe las credenciales del usuario al iniciar sesión.
 */
public class LoginRequestDTO {

    private String correo;
    private String contrasena;

    public LoginRequestDTO() {}

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
}