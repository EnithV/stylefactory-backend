package com.backend.styleFactory.controller;

import com.backend.styleFactory.DTO.ServicioRequestDTO;
import com.backend.styleFactory.DTO.ServicioResponseDTO;
import com.backend.styleFactory.service.ServicioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para el catálogo de servicios.
 * Expone endpoints CRUD bajo la ruta {@code /servicios}.
 * Las consultas GET son públicas (configurado en SecurityConfig);
 * las modificaciones requieren autenticación y roles adecuados.
 */
@RestController
@RequestMapping("/servicios")
public class ServicioController {

    private final ServicioService servicioService;

    public ServicioController(ServicioService servicioService) {
        this.servicioService = servicioService;
    }

    /**
     * Obtiene todos los servicios disponibles.
     * @return Lista de {@link ServicioResponseDTO}.
     */
    @GetMapping
    public ResponseEntity<List<ServicioResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(servicioService.findAll());
    }

    /**
     * Busca un servicio por su ID.
     * @param id Identificador del servicio.
     * @return DTO del servicio, o 404 si no existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ServicioResponseDTO> obtenerPorId(@PathVariable Long id) {
        ServicioResponseDTO dto = servicioService.findById(id);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    /**
     * Crea un nuevo servicio (restringido a ADMIN).
     * @param dto Datos del servicio a crear.
     * @return DTO del servicio creado, con estado 201.
     */
    @PostMapping
    public ResponseEntity<ServicioResponseDTO> crear(@Valid @RequestBody ServicioRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servicioService.save(dto));
    }

    /**
     * Actualiza un servicio existente (restringido a ADMIN).
     * @param id  ID del servicio a modificar.
     * @param dto Nuevos datos del servicio.
     * @return DTO actualizado, o 404 si no se encuentra.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ServicioResponseDTO> actualizar(@PathVariable Long id,
                                                          @Valid @RequestBody ServicioRequestDTO dto) {
        ServicioResponseDTO actualizado = servicioService.update(id, dto);
        if (actualizado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(actualizado);
    }

    /**
     * Elimina un servicio por su ID (restringido a ADMIN).
     * @param id Identificador del servicio a eliminar.
     * @return 204 No Content.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        servicioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}