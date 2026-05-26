package com.backend.styleFactory.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "servicios")
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicio")
    private Long idServicio;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Column(nullable = false, length = 200)
    private String nombre;

    @NotBlank(message = "La descripcion no puede estar vacío")
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

    @NotBlank(message = "El tipo de servicio es obligatorio")
    @Column(name = "tipo", nullable = false, length = 200)
    private String tipoServicio;

//    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL)
//    private List<Reserva> reservas = new ArrayList<>();

    public Servicio() {
    }

    public Servicio( String nombre, String descripcion, String urlImagen, Double precio, String tipoServicio, boolean estado) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.urlImagen = urlImagen;
        this.precio = precio;
        this.tipoServicio = tipoServicio;
        this.estado = estado;
    }

    public Long getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Long idServicio) {
        this.idServicio = idServicio;
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




