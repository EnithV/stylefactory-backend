package com.backend.styleFactory.DTO;

import com.backend.styleFactory.model.Servicio;

public class ServicioResponseDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private String urlImagen;
    private boolean estado = true;
    private Double precio;
    private String tipoServicio;

    public ServicioResponseDTO() {
    }

    public static ServicioResponseDTO desde(Servicio servicio){
        ServicioResponseDTO dto = new ServicioResponseDTO();
        dto.id = servicio.getIdServicio();
        dto.nombre = servicio.getNombre();
        dto.descripcion = servicio.getDescripcion();
        dto.urlImagen = servicio.getUrlImagen();
        dto.precio = servicio.getPrecio();
        dto.tipoServicio = servicio.getTipoServicio();
        dto.estado = servicio.isEstado();
        return dto;
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

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }
}
