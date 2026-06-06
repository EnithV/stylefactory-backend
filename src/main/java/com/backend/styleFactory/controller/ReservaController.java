package com.backend.styleFactory.controller;

import com.backend.styleFactory.DTO.ReservaRequestDTO;
import com.backend.styleFactory.DTO.ReservaResponseDTO;
import com.backend.styleFactory.DTO.SlotOcupadoDTO;
import com.backend.styleFactory.model.Usuario;
import com.backend.styleFactory.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @GetMapping
    public ResponseEntity<List<ReservaResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(reservaService.findAll());
    }

    @GetMapping("/mis-reservas")
    public ResponseEntity<List<ReservaResponseDTO>> misReservas() {
        Usuario usuario = obtenerUsuarioAutenticado();
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(reservaService.findByUsuarioId(usuario.getId()));
    }

    @GetMapping("/ocupadas")
    public ResponseEntity<List<SlotOcupadoDTO>> slotsOcupados(
            @RequestParam Long empleadoId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(reservaService.findSlotsOcupados(empleadoId, fecha));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> obtenerPorId(@PathVariable Long id) {
        ReservaResponseDTO dto = reservaService.findById(id);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<ReservaResponseDTO> crear(@Valid @RequestBody ReservaRequestDTO dto) {
        Usuario usuario = obtenerUsuarioAutenticado();
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        dto.setUsuarioId(usuario.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> actualizar(@PathVariable Long id,
                                                         @Valid @RequestBody ReservaRequestDTO dto) {
        ReservaResponseDTO actualizado = reservaService.update(id, dto);
        if (actualizado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(actualizado);
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<ReservaResponseDTO> actualizarEstado(@PathVariable Long id,
                                                               @RequestBody Map<String, String> body) {
        Usuario usuario = obtenerUsuarioAutenticado();
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String estado = body != null ? body.get("estado") : null;
        return ResponseEntity.ok(reservaService.updateEstado(id, estado, usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        reservaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private Usuario obtenerUsuarioAutenticado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof Usuario usuario)) {
            return null;
        }
        return usuario;
    }
}
