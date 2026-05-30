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

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservaService {

    private static final ZoneId ZONA_COLOMBIA = ZoneId.of("America/Bogota");
    private static final LocalTime HORA_APERTURA = LocalTime.of(9, 0);
    private static final LocalTime HORA_ULTIMO_INICIO = LocalTime.of(18, 0);
    private static final LocalTime HORA_CIERRE_ATENCION = LocalTime.of(20, 0);

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

    public List<ReservaResponseDTO> findByUsuarioId(Long usuarioId) {
        return reservaRepository.findByUsuario_IdOrderByFechaDescHoraDesc(usuarioId)
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

        validarEntidadesActivas(empleado, servicio);
        validarReglasHorario(dto.getFecha(), dto.getHora(), servicio);

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

        validarEntidadesActivas(empleado, servicio);
        validarReglasHorario(dto.getFecha(), dto.getHora(), servicio);

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

    private void validarEntidadesActivas(Empleado empleado, Servicio servicio) {
        if (Boolean.FALSE.equals(empleado.getEstado())) {
            throw new RuntimeException("El estilista seleccionado no está disponible");
        }
        if (!servicio.isEstado()) {
            throw new RuntimeException("El servicio seleccionado no está disponible");
        }
    }

    /**
     * Reglas de horario (Fase 3): atención 9 a.m.–8 p.m.; último inicio de cita 6 p.m.;
     * el cliente puede reservar en cualquier momento del día para fechas futuras o hoy.
     */
    private void validarReglasHorario(LocalDate fecha, LocalTime hora, Servicio servicio) {
        if (fecha == null || hora == null) {
            throw new RuntimeException("La fecha y la hora son obligatorias");
        }

        ZonedDateTime ahoraColombia = ZonedDateTime.now(ZONA_COLOMBIA);
        LocalDate hoy = ahoraColombia.toLocalDate();

        if (fecha.isBefore(hoy)) {
            throw new RuntimeException("No se pueden hacer reservas en fechas pasadas");
        }

        if (hora.isBefore(HORA_APERTURA)) {
            throw new RuntimeException("La primera cita del día puede agendarse desde las 9:00 a.m.");
        }

        if (hora.isAfter(HORA_ULTIMO_INICIO)) {
            throw new RuntimeException("La última hora de inicio permitida es las 6:00 p.m.");
        }

        if (fecha.equals(hoy) && !hora.isAfter(ahoraColombia.toLocalTime())) {
            throw new RuntimeException("No se pueden agendar citas en un horario ya pasado");
        }

        int duracionMinutos = servicio.getDuracionMinutos() != null ? servicio.getDuracionMinutos() : 60;
        LocalTime horaFin = hora.plusMinutes(duracionMinutos);
        if (horaFin.isAfter(HORA_CIERRE_ATENCION)) {
            throw new RuntimeException(
                    "El servicio no alcanza a completarse antes del cierre del salón (8:00 p.m.)");
        }
    }
}