package com.backend.styleFactory.DTO;

import com.backend.styleFactory.model.Empleado;

/**
 * DTO público para mostrar estilistas en el flujo de reservas del frontend.
 */
public class EmpleadoCatalogoDTO {

    private Long id;
    private String nombre;
    private String especialidad;
    private String url;
    private Boolean estado;

    public EmpleadoCatalogoDTO() {}

    public static EmpleadoCatalogoDTO desde(Empleado empleado) {
        EmpleadoCatalogoDTO dto = new EmpleadoCatalogoDTO();
        dto.id = empleado.getId();
        dto.especialidad = empleado.getEspecialidad();
        dto.estado = empleado.getEstado();
        dto.url = empleado.getUrl();
        if (empleado.getUsuario() != null) {
            dto.nombre = empleado.getUsuario().getNombre();
        }
        return dto;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public Boolean getEstado() { return estado; }
    public void setEstado(Boolean estado) { this.estado = estado; }
}
