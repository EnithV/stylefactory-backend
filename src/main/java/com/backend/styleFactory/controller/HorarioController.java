package com.backend.styleFactory.controller;

import com.backend.styleFactory.DTO.HorarioRequestDTO;
import com.backend.styleFactory.DTO.HorarioResponseDTO;
import com.backend.styleFactory.service.HorarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/horarios")
public class HorarioController {

    private final HorarioService horarioService;

    public HorarioController(HorarioService horarioService) {
        this.horarioService = horarioService;
    }

    @PostMapping
    public HorarioResponseDTO guardarHorario(@RequestBody HorarioRequestDTO requestDTO) {
        return horarioService.guardarHorario(requestDTO);
    }

    @GetMapping
    public List<HorarioResponseDTO> listarHorarios() {
        return horarioService.listarHorarios();
    }
}