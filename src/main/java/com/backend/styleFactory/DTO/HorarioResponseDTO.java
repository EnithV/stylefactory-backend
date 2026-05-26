package com.backend.styleFactory.DTO;

import java.time.LocalDateTime;

public class HorarioResponseDTO {

    private Long idHorario;
    private LocalDateTime fechaHora;
    private Long empleadoId;

    // Constructor vacío
    public HorarioResponseDTO() {
    }

    // Constructor con parámetros
    public HorarioResponseDTO(Long idHorario, LocalDateTime fechaHora, Long empleadoId) {
        this.idHorario = idHorario;
        this.fechaHora = fechaHora;
        this.empleadoId = empleadoId;
    }

    // Getters y Setters
    public Long getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(Long idHorario) {
        this.idHorario = idHorario;
    }

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
