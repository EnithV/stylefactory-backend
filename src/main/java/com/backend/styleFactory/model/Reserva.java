package com.backend.styleFactory.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Entidad que representa una reserva de servicio en el sistema.
 * Mapea la tabla {@code reservas} de la base de datos.
 * Una reserva asocia a un cliente (usuario), un empleado (estilista) y un servicio
 * en una fecha y hora determinadas, y posee un estado que indica su situación actual.
 *
 * @author Enith (o tu nombre real)
 * @version 1.2
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
    @JoinColumn(name = "id_usuario", nullable = false)   // Nombre de columna corregido de 'id_usuarios' a 'id_usuario' para reflejar correctamente la cardinalidad de la relación.
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

    // ─────────────────────────────────────────────
    // Getters y Setters
    // ─────────────────────────────────────────────

    /**
     * Obtiene el identificador único de la reserva.
     *
     * @return id de la reserva
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador de la reserva.
     * Utilizado principalmente en pruebas unitarias.
     *
     * @param id Identificador a asignar
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene la fecha en que se agenda la reserva.
     *
     * @return fecha de la reserva
     */
    public LocalDate getFecha() {
        return fecha;
    }

    /**
     * Define la fecha de la reserva.
     *
     * @param fecha nueva fecha
     */
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    /**
     * Obtiene la hora asignada a la reserva.
     *
     * @return hora de la reserva
     */
    public LocalTime getHora() {
        return hora;
    }

    /**
     * Define la hora de la reserva.
     *
     * @param hora nueva hora
     */
    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    /**
     * Obtiene el estado actual de la reserva.
     *
     * @return estado de la reserva (PENDIENTE, CONFIRMADA, etc.)
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Actualiza el estado de la reserva.
     *
     * @param estado nuevo estado
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Obtiene el cliente que realizó la reserva.
     *
     * @return usuario asociado
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Asigna el cliente a la reserva.
     *
     * @param usuario cliente
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene el estilista asignado.
     *
     * @return empleado estilista
     */
    public Empleado getEmpleado() {
        return empleado;
    }

    /**
     * Asigna el estilista a la reserva.
     *
     * @param empleado estilista
     */
    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    /**
     * Obtiene el servicio contratado.
     *
     * @return servicio reservado
     */
    public Servicio getServicio() {
        return servicio;
    }

    /**
     * Asigna el servicio a la reserva.
     *
     * @param servicio servicio
     */
    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }
}