package com.backend.styleFactory.service;

import com.backend.styleFactory.DTO.EmpleadoCatalogoDTO;
import com.backend.styleFactory.DTO.EmpleadoRequestDTO;
import com.backend.styleFactory.DTO.EmpleadoResponseDTO;
import com.backend.styleFactory.model.Empleado;
import com.backend.styleFactory.model.Usuario;
import com.backend.styleFactory.repository.EmpleadoRepository;
import com.backend.styleFactory.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpleadoService {
    private final EmpleadoRepository empleadoRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public EmpleadoService(EmpleadoRepository empleadoRepository, UsuarioRepository usuarioRepository) {
        this.empleadoRepository = empleadoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<EmpleadoResponseDTO> findAll() {
        return empleadoRepository.findAll()
                .stream()
                .map(EmpleadoResponseDTO::desde)
                .collect(Collectors.toList());
    }

    public List<EmpleadoCatalogoDTO> findCatalogoActivos() {
        return empleadoRepository.findAll()
                .stream()
                .filter(e -> Boolean.TRUE.equals(e.getEstado()))
                .map(EmpleadoCatalogoDTO::desde)
                .collect(Collectors.toList());
    }

    public EmpleadoResponseDTO findById(Long id) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con id: " + id));
        return EmpleadoResponseDTO.desde(empleado);
    }

    public EmpleadoResponseDTO save(EmpleadoRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuario_id())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + dto.getUsuario_id()));
        Empleado empleado = new Empleado(usuario, dto.getEspecialidad(), dto.getEstado(), dto.getUrl());
        return EmpleadoResponseDTO.desde(empleadoRepository.save(empleado));
    }

    public EmpleadoResponseDTO update(Long id, EmpleadoRequestDTO dto) {
        Empleado existe = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con id: " + id));
        Usuario usuario = usuarioRepository.findById(dto.getUsuario_id())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + dto.getUsuario_id()));
        existe.setUsuario(usuario);
        existe.setEspecialidad(dto.getEspecialidad());
        existe.setEstado(dto.getEstado());
        existe.setUrl(dto.getUrl());
        return EmpleadoResponseDTO.desde(empleadoRepository.save(existe));
    }

    public void delete(Long id) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con id: " + id));
        empleado.setEstado(false);               // borrado lógico
        empleadoRepository.save(empleado);
    }
}