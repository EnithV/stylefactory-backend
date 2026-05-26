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
import com.backend.styleFactory.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final UsuarioRepository usuarioRepository;
    private final EmpleadoRepository empleadoRepository;
    private final ServicioRepository servicioRepository;

    public ReservaService(ReservaRepository reservaRepository,
                          UsuarioRepository usuarioRepository,
                          EmpleadoRepository empleadoRepository,
                          ServicioRepository servicioRepository) {
        this.reservaRepository = reservaRepository;
        this.usuarioRepository = usuarioRepository;
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
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con id: " + id));
    }

    public ReservaResponseDTO save(ReservaRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + dto.getUsuarioId()));
        Empleado empleado = empleadoRepository.findById(dto.getEmpleadoId())
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con id: " + dto.getEmpleadoId()));
        Servicio servicio = servicioRepository.findById(dto.getServicioId())
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado con id: " + dto.getServicioId()));

        String estado = dto.getEstado();
        if (estado == null || estado.isBlank()) {
            estado = "PENDIENTE";
        }

        Reserva reserva = new Reserva(dto.getFecha(), dto.getHora(), estado, usuario, empleado, servicio);
        return ReservaResponseDTO.desde(reservaRepository.save(reserva));
    }

    public ReservaResponseDTO update(Long id, ReservaRequestDTO dto) {
        Reserva existente = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con id: " + id));

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + dto.getUsuarioId()));
        Empleado empleado = empleadoRepository.findById(dto.getEmpleadoId())
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con id: " + dto.getEmpleadoId()));
        Servicio servicio = servicioRepository.findById(dto.getServicioId())
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado con id: " + dto.getServicioId()));

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
        if (!reservaRepository.existsById(id)) {
            throw new RuntimeException("Reserva no encontrada con id: " + id);
        }
        reservaRepository.deleteById(id);
    }
}