package com.backend.styleFactory.repository;

import com.backend.styleFactory.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repositorio para la entidad {@link Reserva}.
 * Maneja la persistencia de las reservas realizadas por los clientes.
 */
@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByUsuario_IdOrderByFechaDescHoraDesc(Long usuarioId);

    List<Reserva> findByEmpleado_IdAndFecha(Long empleadoId, LocalDate fecha);
}