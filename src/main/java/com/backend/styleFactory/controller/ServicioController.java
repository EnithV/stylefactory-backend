package com.backend.styleFactory.controller;

import com.backend.styleFactory.DTO.ServicioRequestDTO;
import com.backend.styleFactory.DTO.ServicioResponseDTO;
import com.backend.styleFactory.service.ServicioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicios")
public class ServicioController {

    private final ServicioService servicioService;

    public ServicioController(ServicioService servicioService) {
        this.servicioService = servicioService;
    }

    @GetMapping
    @SecurityRequirements
    public ResponseEntity<List<ServicioResponseDTO>> listarServicios() {
        return ResponseEntity.ok(servicioService.findAll());
    }

    @GetMapping("/{id}")
    @SecurityRequirements
    public ResponseEntity<ServicioResponseDTO> obtenerPorId(@PathVariable Long id) {
        ServicioResponseDTO servicio = servicioService.findById(id);
        if (servicio == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(servicio);
    }

    @PostMapping
    public ResponseEntity<ServicioResponseDTO> crear(@Valid @RequestBody ServicioRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servicioService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicioResponseDTO> actualizar(@PathVariable Long id,
                                                          @Valid @RequestBody ServicioRequestDTO dto) {
        ServicioResponseDTO actualizado = servicioService.update(id, dto);
        if (actualizado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        servicioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}