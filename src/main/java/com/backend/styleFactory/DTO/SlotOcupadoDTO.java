package com.backend.styleFactory.DTO;

import java.time.LocalTime;

/**
 * Franja horaria ocupada por una reserva activa (consulta pública de disponibilidad).
 */
public class SlotOcupadoDTO {

    private String hora;
    private int duracionMinutos;

    public SlotOcupadoDTO() {}

    public SlotOcupadoDTO(LocalTime hora, int duracionMinutos) {
        this.hora = hora != null ? hora.toString().substring(0, 5) : null;
        this.duracionMinutos = duracionMinutos;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getDuracionMinutos() {
        return duracionMinutos;
    }

    public void setDuracionMinutos(int duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }
}
