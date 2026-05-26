package com.backend.styleFactory.service;

import com.backend.styleFactory.DTO.EmpleadoRequestDTO;
import com.backend.styleFactory.DTO.EmpleadoResponseDTO;
import com.backend.styleFactory.model.Empleado;
import com.backend.styleFactory.model.Usuario;
import com.backend.styleFactory.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpleadoService {
    private final EmpleadoRepository empleadoRepository;
    private final UsuarioRespository usuarioRepository;

    @Autowired
    public EmpleadoService(EmpleadoRepository empleadoRepository, UsuarioRespository usuarioRepository) {
        this.empleadoRepository = empleadoRepository;
        this.usuarioRepository = usuarioRepository;

    }
    public List<EmpleadoResponseDTO> findAll() {
        return empleadoRepository.findAll()
                .stream()
                .map(EmpleadoResponseDTO::desde)
                .collect(Collectors.toList());
    }

    public EmpleadoResponseDTO findById(Long id){
        Empleado empleado = empleadoRepository.findById(id).orElse(null);
        if (empleado == null) return null;
        return EmpleadoResponseDTO.desde(empleado);
    }

    public EmpleadoResponseDTO save(EmpleadoRequestDTO dto){
        Usuario usuario = usuarioRepository.findById(dto.getUsuario_id()).orElse(null);
        Empleado empleado = new Empleado(usuario, dto.getEspecialida(), dto.getEstado(), dto.getUrl());
        return EmpleadoResponseDTO.desde(empleadoRepository.save(empleado));
    }

    public EmpleadoResponseDTO update(Long id, EmpleadoRequestDTO dto) {
       Empleado existe = empleadoRepository.findById(id).orElse(null);
        if (existe == null) return null;
        Usuario usuario = usuarioRepository.findById(dto.getUsuario_id()).orElse(null);
        existe.setUsuario(usuario);
        existe.setEspecialidad(dto.getEspecialida());
        existe.setEstado(dto.getEstado());
        existe.setUrl(dto.getUrl());
        return EmpleadoResponseDTO.desde(empleadoRepository.save(existe));
    }

    public void delete(Long id){
        empleadoRepository.deleteById(id);
    }
}
