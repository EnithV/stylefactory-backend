package com.backend.styleFactory.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EmpleadoRequestDTO {

    @NotNull(message = "El usuario_id no puede estar vacío")
    private Long usuario_id;

    @NotBlank(message = "La especialidad no puede estar vacía")
    private String especialidad;          // ← corregido

    @NotNull(message = "El estado tiene que tener un valor")
    private Boolean estado;

    @NotBlank(message = "Tienes que ingresar una URL")
    private String url;

    public EmpleadoRequestDTO() {}

    public Long getUsuario_id() { return usuario_id; }
    public void setUsuario_id(Long usuario_id) { this.usuario_id = usuario_id; }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public Boolean getEstado() { return estado; }
    public void setEstado(Boolean estado) { this.estado = estado; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
}