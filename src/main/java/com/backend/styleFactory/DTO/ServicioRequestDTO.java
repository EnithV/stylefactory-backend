package com.backend.styleFactory.DTO;

import jakarta.validation.constraints.*;

public class ServicioRequestDTO {
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
    private String tipoServicio;

    @NotNull(message = "La duración estimada es obligatoria")
    @Min(value = 15, message = "La duración mínima es 15 minutos")
    @Max(value = 480, message = "La duración máxima es 480 minutos")
    private Integer duracionMinutos;

    public ServicioRequestDTO() {
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

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServico) {
        this.tipoServicio = tipoServico;
    }

    public Integer getDuracionMinutos() {
        return duracionMinutos;
    }

    public void setDuracionMinutos(Integer duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }
}
