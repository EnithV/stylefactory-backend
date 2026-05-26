package com.backend.styleFactory.service;

import com.backend.styleFactory.DTO.UsuarioRequestDTO;
import com.backend.styleFactory.DTO.UsuarioResponseDTO;
import com.backend.styleFactory.model.Usuario;
import com.backend.styleFactory.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Crear usuario
    public UsuarioResponseDTO crearUsuario(UsuarioRequestDTO dto) {
        if (usuarioRepository.existsByCorreo(dto.getCorreo())) {
            throw new RuntimeException("Ya existe un usuario con ese correo");
        }
        Usuario usuario = new Usuario(
                dto.getNombre(),
                dto.getCorreo(),
                dto.getTelefono(),
                dto.getContrasena(),
                dto.getRol(),
                true
        );

        Usuario guardado = usuarioRepository.save(usuario);
        return mapearAResponse(guardado);
    }

    // Listar todos los usuarios activos
    public List<UsuarioResponseDTO> listarUsuarios() {
        return usuarioRepository.findByEstadoTrue()
                .stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    // Obtener por ID
    public UsuarioResponseDTO obtenerPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
        return mapearAResponse(usuario);
    }

    // Actualizar usuario
    public UsuarioResponseDTO actualizarUsuario(Long id, UsuarioRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        usuario.setNombre(dto.getNombre());
        usuario.setCorreo(dto.getCorreo());
        usuario.setTelefono(dto.getTelefono());
        usuario.setContrasena(dto.getContrasena());
        usuario.setRol(dto.getRol());

        Usuario actualizado = usuarioRepository.save(usuario);
        return mapearAResponse(actualizado);
    }

    // Soft delete — no borra de la BD, solo desactiva
    public void eliminarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
        usuario.setEstado(false);
        usuarioRepository.save(usuario);
    }

    // Método privado reutilizable para mapear entidad → DTO
    private UsuarioResponseDTO mapearAResponse(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreo(),
                usuario.getTelefono(),
                usuario.getRol(),
                usuario.isEstado()
        );
    }
}