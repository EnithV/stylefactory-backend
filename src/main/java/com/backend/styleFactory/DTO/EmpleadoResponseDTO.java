package com.backend.styleFactory.DTO;

import com.backend.styleFactory.model.Empleado;

public class EmpleadoResponseDTO {
    private Long id;
    private Long usuario_id;
    private String especialidad;        // ← corregido
    private Boolean estado;
    private String url;

    public EmpleadoResponseDTO() {}

    public static EmpleadoResponseDTO desde(Empleado empleado) {
        EmpleadoResponseDTO dto = new EmpleadoResponseDTO();
        dto.id = empleado.getId();
        dto.usuario_id = empleado.getUsuario().getId();
        dto.especialidad = empleado.getEspecialidad();
        dto.estado = empleado.getEstado();
        dto.url = empleado.getUrl();
        return dto;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUsuario_id() { return usuario_id; }
    public void setUsuario_id(Long usuario_id) { this.usuario_id = usuario_id; }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public Boolean getEstado() { return estado; }
    public void setEstado(Boolean estado) { this.estado = estado; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
}