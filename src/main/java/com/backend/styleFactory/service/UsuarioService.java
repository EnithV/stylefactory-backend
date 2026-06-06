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

    /**
     * Crea un nuevo usuario validando que el correo no esté ya registrado.
     *
     * @param dto Datos del usuario a crear.
     * @return UsuarioResponseDTO con los datos del usuario guardado.
     * @throws RuntimeException si el correo ya existe.
     */
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

    /**
     * Lista todos los usuarios activos (estado = true).
     *
     * @return Lista de UsuarioResponseDTO.
     */
    public List<UsuarioResponseDTO> listarUsuarios() {
        return usuarioRepository.findByEstadoTrue()
                .stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    /**
     * Busca un usuario por su ID.
     *
     * @param id Identificador del usuario.
     * @return UsuarioResponseDTO correspondiente.
     * @throws RuntimeException si el usuario no existe.
     */
    public UsuarioResponseDTO obtenerPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
        return mapearAResponse(usuario);
    }

    /**
     * Actualiza los datos de un usuario existente.
     * Si se cambia el correo, verifica que el nuevo correo no esté siendo usado por otro usuario.
     *
     * @param id  ID del usuario a actualizar.
     * @param dto Datos nuevos del usuario.
     * @return UsuarioResponseDTO actualizado.
     * @throws RuntimeException si el usuario no existe o si el correo ya está en uso.
     */
    public UsuarioResponseDTO actualizarUsuario(Long id, UsuarioRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        // Validación de correo duplicado (excluyendo al propio usuario)
        if (!usuario.getCorreo().equals(dto.getCorreo()) &&
                usuarioRepository.existsByCorreo(dto.getCorreo())) {
            throw new RuntimeException("El correo ya está en uso por otro usuario");
        }

        usuario.setNombre(dto.getNombre());
        usuario.setCorreo(dto.getCorreo());
        usuario.setTelefono(dto.getTelefono());
        if (dto.getContrasena() != null && !dto.getContrasena().isBlank()) {
            usuario.setContrasena(dto.getContrasena());
        }
        if (dto.getRol() != null) {
            usuario.setRol(dto.getRol());
        }

        Usuario actualizado = usuarioRepository.save(usuario);
        return mapearAResponse(actualizado);
    }

    /**
     * Realiza un borrado lógico del usuario (estado = false).
     *
     * @param id ID del usuario a desactivar.
     * @throws RuntimeException si el usuario no existe.
     */
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