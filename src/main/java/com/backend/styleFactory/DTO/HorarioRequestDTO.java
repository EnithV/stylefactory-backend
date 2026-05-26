package com.backend.styleFactory.DTO;

import java.time.LocalDateTime;

public class HorarioRequestDTO {

    private LocalDateTime fechaHora;
    private Long empleadoId;

    // Constructor vacío
    public HorarioRequestDTO() {
    }

    // Getters y Setters
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Long getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(Long empleadoId) {
        this.empleadoId = empleadoId;
    }
}