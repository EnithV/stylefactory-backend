package com.backend.styleFactory.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;

public class ServiceRequestDTO {
    @NotNull(message = "El nombre del servicio es obligatorio")
    private String nombre;
    @NotNull(message = "la descripcion del servicio es obligatoria")
    private String descripcion;
    @NotNull(message = "La imagen del servicio es obligatorio")
    private String urlImagen;
    private boolean estado = true;
    @NotNull(message = "El precio del servicio es obligatorio")
    private Double precio;
    @NotNull(message = "El tipo de servicio es obligatorio")
    private String tipoServico;

    public ServiceRequestDTO() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getTipoServico() {
        return tipoServico;
    }

    public void setTipoServico(String tipoServico) {
        this.tipoServico = tipoServico;
    }
}
