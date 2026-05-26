package com.backend.styleFactory.controller;

import com.backend.styleFactory.DTO.UsuarioRequestDTO;
import com.backend.styleFactory.DTO.UsuarioResponseDTO;
import com.backend.styleFactory.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la administración de usuarios.
 * Expone endpoints CRUD bajo la ruta {@code /usuarios}.
 */
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Crea un nuevo usuario validando los datos de entrada.
     *
     * @param dto Datos del usuario a crear.
     * @return Usuario creado con estado 201.
     */
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crearUsuario(@Valid @RequestBody UsuarioRequestDTO dto) {
        UsuarioResponseDTO response = usuarioService.crearUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Lista todos los usuarios activos.
     *
     * @return Lista de usuarios.
     */
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    /**
     * Obtiene un usuario por su identificador.
     *
     * @param id ID del usuario.
     * @return Usuario encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.obtenerPorId(id));
    }

    /**
     * Actualiza un usuario existente aplicando validaciones sobre los nuevos datos.
     *
     * @param id  ID del usuario a modificar.
     * @param dto Nuevos datos del usuario.
     * @return Usuario actualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizarUsuario(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequestDTO dto) {
        return ResponseEntity.ok(usuarioService.actualizarUsuario(id, dto));
    }

    /**
     * Desactiva un usuario (borrado lógico).
     *
     * @param id ID del usuario a desactivar.
     * @return Respuesta sin contenido (204).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}