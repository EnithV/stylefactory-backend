package com.backend.styleFactory.service;

import com.backend.styleFactory.DTO.HorarioRequestDTO;
import com.backend.styleFactory.DTO.HorarioResponseDTO;
import com.backend.styleFactory.model.Empleado;
import com.backend.styleFactory.model.Horario;
import com.backend.styleFactory.repository.EmpleadoRepository;
import com.backend.styleFactory.repository.HorarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HorarioService {

    private final HorarioRepository horarioRepository;
    private final EmpleadoRepository empleadoRepository;

    public HorarioService(HorarioRepository horarioRepository,
                          EmpleadoRepository empleadoRepository) {
        this.horarioRepository = horarioRepository;
        this.empleadoRepository = empleadoRepository;
    }

    /**
     * Guarda un nuevo horario asociado a un empleado existente.
     * Si el empleado no se encuentra, lanza una excepción.
     *
     * @param requestDTO Datos del horario (fecha/hora y empleadoId)
     * @return HorarioResponseDTO con los datos guardados
     */
    public HorarioResponseDTO guardarHorario(HorarioRequestDTO requestDTO) {
        Empleado empleado = empleadoRepository.findById(requestDTO.getEmpleadoId())
                .orElseThrow(() -> new RuntimeException(
                        "Empleado no encontrado con id: " + requestDTO.getEmpleadoId()));

        Horario horario = new Horario(empleado, requestDTO.getFechaHora());
        Horario guardado = horarioRepository.save(horario);

        return new HorarioResponseDTO(
                guardado.getIdHorario(),
                guardado.getFechaHora(),
                guardado.getEmpleado().getId()
        );
    }

    /**
     * Lista todos los horarios registrados, incluyendo el ID del empleado asociado.
     *
     * @return Lista de HorarioResponseDTO
     */
    public List<HorarioResponseDTO> listarHorarios() {
        return horarioRepository.findAll().stream()
                .map(horario -> new HorarioResponseDTO(
                        horario.getIdHorario(),
                        horario.getFechaHora(),
                        horario.getEmpleado() != null ? horario.getEmpleado().getId() : null
                ))
                .collect(Collectors.toList());
    }
}