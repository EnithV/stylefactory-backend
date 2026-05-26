package com.backend.styleFactory.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "horarios")
public class Horario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHorario;

    @ManyToOne
    @JoinColumn(name = "id_empleado", nullable = false)
    private Empleado empleado;

    @Column(nullable = false)
    private LocalDateTime fechaHora;

    // Constructor vacío obligatorio para JPA
    public Horario() {}

    // Constructor con parámetros (Corregido el segundo parámetro)
    public Horario(Empleado empleado, LocalDateTime fechaHora) {
        this.empleado = empleado;
        this.fechaHora = fechaHora;
    }

    // Getters y Setters de idHorario
    public Long getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(Long idHorario) {
        this.idHorario = idHorario;
    }

    // Getters y Setters de Empleado (Ya metidos dentro de la clase)
    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    // Getters y Setters de fechaHora (Bien estructurados y cerrados)
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }
}
