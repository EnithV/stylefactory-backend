package com.backend.styleFactory.controller;

import com.backend.styleFactory.DTO.ServicioResponseDTO;
import com.backend.styleFactory.service.ServicioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Servicios", description = "Gestion de servicios de reservas")
@RestController
@RequestMapping("/servicios")
public class ServicioController {


    private final ServicioService servicioService;

    @Autowired
    public ServicioController(ServicioService servicioService) {
        this.servicioService = servicioService;
    }

    @Operation(summary = "Listar todos los servicios")
    @GetMapping
    public ResponseEntity<List<ServicioResponseDTO>> listarServicios(){
        return ResponseEntity.ok(servicioService.findAll());
    }

    @Operation(summary = "Buscar servicio por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ServicioResponseDTO> obtenerPorId(@PathVariable Long id){
        ServicioResponseDTO servicio = servicioService.findById(id);
        if (servicio == null) return null;
        return ResponseEntity.ok(servicio);
    }

    @Operation(summary = "Crear servcios")
    @PostMapping
    public ResponseEntity<ServicioResponseDTO> crear(@Valid @RequestBody ServicioResponseDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(servicioService.save(dto));
    }

    @Operation(summary = "Actualizar servicio")
    @PutMapping
    public ResponseEntity<ServicioResponseDTO> actualizar(@PathVariable Long id,
                                                         @RequestBody ServicioResponseDTO dto){
        ServicioResponseDTO actualizado = servicioService.update(id, dto);
        if (actualizado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(actualizado);
    }

    @Operation(summary = "Eliminar servicio por ID ")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> Eliminar(@PathVariable Long id){
        servicioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
