package com.backend.styleFactory.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EmpleadoRequestDTO {

    @NotNull(message = "el usuario_id no puede estar vacio")
    private Long usuario_id;

    @NotBlank(message = "la especialidad no puede estar vacia")
    private String especialida;

    @NotNull(message = "El estado tiene que tener un valor" )
    private Boolean estado;

    @NotBlank(message = "Tienes que ingresar una url")
    private String url;

    public EmpleadoRequestDTO() {
    }

    public Long getUsuario_id() {
        return usuario_id;
    }

    public String getEspecialida() {
        return especialida;
    }

    public Boolean getEstado() {
        return estado;
    }

    public String getUrl() {
        return url;
    }
}
