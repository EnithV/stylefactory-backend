package com.backend.styleFactory.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "servicios")
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicio")
    private Long idServico;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Column(nullable = false, length = 200)
    private String nombre;

    @NotBlank(message = "El descripcion no puede estar vacío")
    @Column(nullable = false)
    private String descripcion;

    @NotBlank(message = "La imagen es obligatorio")
    @Column(name = "url_imagen", nullable = false, length = 300)
    private String urlImagen;

    @Column( nullable = false)
    private boolean estado = true;

    @NotNull(message = "El precio es obligatorio")
    @Min(value = 0, message = "El precio no puede ser negativo")
    @Column( nullable = false)
    private Double precio;

    @NotBlank(message = "La tipo de servicio es obligatorio")
    @Column(name = "tipo", nullable = false, length = 200)
    private String tipoServico;

    public Servicio() {
    }

    public Servicio(Long idServico, String nombre, String descripcion, String urlImagen, boolean estado, Double precio, String tipoServico) {
        this.idServico = idServico;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.urlImagen = urlImagen;
        this.estado = estado;
        this.precio = precio;
        this.tipoServico = tipoServico;
    }

    public Long getIdServico() {
        return idServico;
    }

    public void setIdServico(Long idServico) {
        this.idServico = idServico;
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




