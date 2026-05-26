package com.backend.styleFactory.service;

import com.backend.styleFactory.DTO.HorarioRequestDTO;
import com.backend.styleFactory.DTO.HorarioResponseDTO;
import com.backend.styleFactory.model.Horario;
import com.backend.styleFactory.repository.HorarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HorarioService {

    private final HorarioRepository horarioRepository;

    public HorarioService(HorarioRepository horarioRepository) {
        this.horarioRepository = horarioRepository;
    }

    // Guardar horario
    public HorarioResponseDTO guardarHorario(HorarioRequestDTO requestDTO) {

        Horario horario = new Horario();
        horario.setFechaHora(requestDTO.getFechaHora());

        Horario horarioGuardado = horarioRepository.save(horario);

        return new HorarioResponseDTO(
                horarioGuardado.getIdHorario(),
                horarioGuardado.getFechaHora(),
                requestDTO.getEmpleadoId()
        );
    }

    // Listar horarios
    public List<HorarioResponseDTO> listarHorarios() {

        return horarioRepository.findAll().stream()
                .map(horario -> new HorarioResponseDTO(
                        horario.getIdHorario(),
                        horario.getFechaHora(),
                        null
                ))
                .collect(Collectors.toList());
    }
}