package com.backend.styleFactory.controller;

import com.backend.styleFactory.DTO.EmpleadoRequestDTO;
import com.backend.styleFactory.DTO.EmpleadoResponseDTO;
import com.backend.styleFactory.model.Empleado;
import com.backend.styleFactory.repository.EmpleadoRepository;
import com.backend.styleFactory.service.EmpleadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    @Autowired
    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping
    public ResponseEntity<List<EmpleadoResponseDTO>> obtenerTodos(){
        return ResponseEntity.ok(empleadoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDTO> obtenerPorId(@PathVariable Long id){
        EmpleadoResponseDTO empleado  = empleadoService.findById(id);
        if(empleado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(empleado);
    }

    @PostMapping
    public ResponseEntity<EmpleadoResponseDTO> crear(@Valid  @RequestBody EmpleadoRequestDTO dto) {
        return ResponseEntity.status(CREATED).body(empleadoService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody EmpleadoRequestDTO dto) {
        EmpleadoResponseDTO actualizado = empleadoService.update(id, dto);
        if(actualizado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(actualizado);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        empleadoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
