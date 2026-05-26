package com.backend.styleFactory.repository;

import com.backend.styleFactory.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad {@link Reserva}.
 * Maneja la persistencia de las reservas realizadas por los clientes.
 */
@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
}