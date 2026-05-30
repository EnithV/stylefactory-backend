package com.backend.styleFactory.controller;

import com.backend.styleFactory.DTO.ReservaRequestDTO;
import com.backend.styleFactory.DTO.ReservaResponseDTO;
import com.backend.styleFactory.model.Usuario;
import com.backend.styleFactory.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controlador REST para la gestión de reservas.
 * Expone endpoints CRUD bajo la ruta {@code /reservas}.
 */
@RestController
@RequestMapping("/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    /**
     * Obtiene todas las reservas registradas.
     *
     * @return Lista de reservas.
     */
    @GetMapping
    public ResponseEntity<List<ReservaResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(reservaService.findAll());
    }

    /**
     * Reservas del usuario autenticado (área personal del cliente).
     */
    @GetMapping("/mis-reservas")
    public ResponseEntity<List<ReservaResponseDTO>> misReservas() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof Usuario usuario)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(reservaService.findByUsuarioId(usuario.getId()));
    }

    /**
     * Busca una reserva por su identificador.
     *
     * @param id ID de la reserva.
     * @return Reserva encontrada o 404 si no existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> obtenerPorId(@PathVariable Long id) {
        ReservaResponseDTO dto = reservaService.findById(id);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    /**
     * Crea una nueva reserva a partir de los datos proporcionados.
     *
     * @param dto Datos de la reserva (fecha, hora, usuario, empleado, servicio).
     * @return Reserva creada con estado 201.
     */
    @PostMapping
    public ResponseEntity<ReservaResponseDTO> crear(@Valid @RequestBody ReservaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaService.save(dto));
    }

    /**
     * Actualiza los datos de una reserva existente.
     *
     * @param id  ID de la reserva a modificar.
     * @param dto Nuevos datos de la reserva.
     * @return Reserva actualizada o 404 si no existe.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> actualizar(@PathVariable Long id,
                                                         @Valid @RequestBody ReservaRequestDTO dto) {
        ReservaResponseDTO actualizado = reservaService.update(id, dto);
        if (actualizado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(actualizado);
    }

    /**
     * Cambia únicamente el estado de una reserva (panel admin).
     */
    @PatchMapping("/{id}/estado")
    public ResponseEntity<ReservaResponseDTO> actualizarEstado(@PathVariable Long id,
                                                               @RequestBody Map<String, String> body) {
        String estado = body != null ? body.get("estado") : null;
        return ResponseEntity.ok(reservaService.updateEstado(id, estado));
    }

    /**
     * Elimina una reserva por su identificador.
     *
     * @param id ID de la reserva a eliminar.
     * @return Respuesta sin contenido (204).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        reservaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}