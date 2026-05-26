package com.backend.styleFactory.DTO;

import com.backend.styleFactory.model.Servicio;

/**
 * DTO de respuesta para mostrar un servicio.
 */
public class ServicioResponseDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private String urlImagen;
    private String tipo;
    private Double precio;

    public ServicioResponseDTO() {}

    /**
     * Construye un DTO de respuesta a partir de la entidad Servicio.
     *
     * @param servicio entidad de origen.
     * @return instancia de ServicioResponseDTO.
     */
    public static ServicioResponseDTO desde(Servicio servicio) {
        ServicioResponseDTO dto = new ServicioResponseDTO();
        dto.id = servicio.getIdServicio();     // método corregido
        dto.nombre = servicio.getNombre();
        dto.descripcion = servicio.getDescripcion();
        dto.urlImagen = servicio.getUrlImagen();
        dto.tipo = servicio.getTipo();
        dto.precio = servicio.getPrecio();
        return dto;
    }

    // Getters y Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getUrlImagen() { return urlImagen; }
    public void setUrlImagen(String urlImagen) { this.urlImagen = urlImagen; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }
}