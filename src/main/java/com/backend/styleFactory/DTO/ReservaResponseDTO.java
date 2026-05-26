package com.backend.styleFactory.DTO;

import com.backend.styleFactory.model.Reserva;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO de respuesta para la entidad {@link Reserva}.
 * Incluye los nombres o identificadores de las entidades relacionadas
 * para facilitar la visualización en el frontend.
 */
public class ReservaResponseDTO {

    private Long id;
    private LocalDate fecha;
    private LocalTime hora;
    private String estado;
    private String nombreUsuario;
    private String nombreEmpleado;
    private String nombreServicio;

    public ReservaResponseDTO() {}

    public static ReservaResponseDTO desde(Reserva reserva) {
        ReservaResponseDTO dto = new ReservaResponseDTO();
        dto.id = reserva.getId();
        dto.fecha = reserva.getFecha();
        dto.hora = reserva.getHora();
        dto.estado = reserva.getEstado();
        if (reserva.getUsuario() != null) {
            dto.nombreUsuario = reserva.getUsuario().getNombre();
        }
        if (reserva.getEmpleado() != null) {
            dto.nombreEmpleado = reserva.getEmpleado().getUsuario() != null ?
                    reserva.getEmpleado().getUsuario().getNombre() : "Sin nombre";
        }
        if (reserva.getServicio() != null) {
            dto.nombreServicio = reserva.getServicio().getNombre();
        }
        return dto;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public String getNombreEmpleado() { return nombreEmpleado; }
    public void setNombreEmpleado(String nombreEmpleado) { this.nombreEmpleado = nombreEmpleado; }

    public String getNombreServicio() { return nombreServicio; }
    public void setNombreServicio(String nombreServicio) { this.nombreServicio = nombreServicio; }
}