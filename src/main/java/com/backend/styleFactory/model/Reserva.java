package com.backend.styleFactory.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Entidad que representa una reserva de servicio en el sistema.
 * Mapea la tabla {@code reservas} de la base de datos.
 * Una reserva asocia a un cliente (usuario), un empleado (estilista) y un servicio
 * en una fecha y hora determinadas, y posee un estado que indica su situación actual.
 */
@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva")
    private Long id;

    /** Fecha en la que se agenda la reserva. */
    @Column(nullable = false)
    private LocalDate fecha;

    /** Hora exacta asignada para la prestación del servicio. */
    @Column(nullable = false)
    private LocalTime hora;

    /**
     * Estado de la reserva (ej. PENDIENTE, CONFIRMADA, CANCELADA, COMPLETADA).
     * Permite realizar seguimiento del ciclo de vida de la reserva.
     */
    @Column(nullable = false)
    private String estado;

    /** Cliente que realiza la reserva. */
    @ManyToOne
    @JoinColumn(name = "id_usuarios", nullable = false)
    private Usuario usuario;

    /** Estilista asignado para ejecutar el servicio. */
    @ManyToOne
    @JoinColumn(name = "id_empleado", nullable = false)
    private Empleado empleado;

    /** Servicio que se reserva (corte, coloración, etc.). */
    @ManyToOne
    @JoinColumn(name = "id_servicio", nullable = false)
    private Servicio servicio;

    /**
     * Constructor por defecto requerido por JPA.
     */
    public Reserva() {}

    /**
     * Constructor completo para crear una instancia de Reserva con todos sus atributos.
     *
     * @param fecha    Fecha programada de la reserva.
     * @param hora     Hora de inicio del servicio.
     * @param estado   Estado inicial de la reserva.
     * @param usuario  Cliente que agenda.
     * @param empleado Estilista asignado.
     * @param servicio Servicio contratado.
     */
    public Reserva(LocalDate fecha, LocalTime hora, String estado,
                   Usuario usuario, Empleado empleado, Servicio servicio) {
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
        this.usuario = usuario;
        this.empleado = empleado;
        this.servicio = servicio;
    }

    // Métodos de acceso (Getters y Setters)

    public Long getId() { return id; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Empleado getEmpleado() { return empleado; }
    public void setEmpleado(Empleado empleado) { this.empleado = empleado; }

    public Servicio getServicio() { return servicio; }
    public void setServicio(Servicio servicio) { this.servicio = servicio; }
}