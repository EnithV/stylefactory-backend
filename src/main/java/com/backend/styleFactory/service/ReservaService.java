package com.backend.styleFactory.service;

import com.backend.styleFactory.DTO.ReservaRequestDTO;
import com.backend.styleFactory.DTO.ReservaResponseDTO;
import com.backend.styleFactory.model.Empleado;
import com.backend.styleFactory.model.Reserva;
import com.backend.styleFactory.model.Servicio;
import com.backend.styleFactory.model.Usuario;
import com.backend.styleFactory.repository.EmpleadoRepository;
import com.backend.styleFactory.repository.ReservaRepository;
import com.backend.styleFactory.repository.ServicioRepository;
import com.backend.styleFactory.repository.UsuarioRespository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Lógica de negocio para la entidad {@link Reserva}.
 * Gestiona las reservas de servicios realizadas por los clientes.
 */
@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final UsuarioRespository usuarioRespository;
    private final EmpleadoRepository empleadoRepository;
    private final ServicioRepository servicioRepository;

    public ReservaService(ReservaRepository reservaRepository,
                          UsuarioRespository usuarioRespository,
                          EmpleadoRepository empleadoRepository,
                          ServicioRepository servicioRepository) {
        this.reservaRepository = reservaRepository;
        this.usuarioRespository = usuarioRespository;
        this.empleadoRepository = empleadoRepository;
        this.servicioRepository = servicioRepository;
    }

    public List<ReservaResponseDTO> findAll() {
        return reservaRepository.findAll()
                .stream()
                .map(ReservaResponseDTO::desde)
                .collect(Collectors.toList());
    }

    public ReservaResponseDTO findById(Long id) {
        return reservaRepository.findById(id)
                .map(ReservaResponseDTO::desde)
                .orElse(null);
    }

    public ReservaResponseDTO save(ReservaRequestDTO dto) {
        Usuario usuario = usuarioRespository.findById(dto.getUsuarioId()).orElse(null);
        Empleado empleado = empleadoRepository.findById(dto.getEmpleadoId()).orElse(null);
        Servicio servicio = servicioRepository.findById(dto.getServicioId()).orElse(null);

        String estado = dto.getEstado();
        if (estado == null || estado.isBlank()) {
            estado = "PENDIENTE";
        }

        Reserva reserva = new Reserva(dto.getFecha(), dto.getHora(), estado,
                usuario, empleado, servicio);
        return ReservaResponseDTO.desde(reservaRepository.save(reserva));
    }

    public ReservaResponseDTO update(Long id, ReservaRequestDTO dto) {
        Reserva existente = reservaRepository.findById(id).orElse(null);
        if (existente == null) return null;

        Usuario usuario = usuarioRespository.findById(dto.getUsuarioId()).orElse(null);
        Empleado empleado = empleadoRepository.findById(dto.getEmpleadoId()).orElse(null);
        Servicio servicio = servicioRepository.findById(dto.getServicioId()).orElse(null);

        existente.setFecha(dto.getFecha());
        existente.setHora(dto.getHora());
        existente.setEstado(dto.getEstado() != null ? dto.getEstado() : existente.getEstado());
        existente.setUsuario(usuario);
        existente.setEmpleado(empleado);
        existente.setServicio(servicio);

        reservaRepository.save(existente);
        return ReservaResponseDTO.desde(existente);
    }

    public void delete(Long id) {
        reservaRepository.deleteById(id);
    }
}