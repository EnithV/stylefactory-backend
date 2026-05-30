package com.backend.styleFactory.controller;

import com.backend.styleFactory.DTO.EmpleadoCatalogoDTO;
import com.backend.styleFactory.DTO.EmpleadoRequestDTO;
import com.backend.styleFactory.DTO.EmpleadoResponseDTO;
import com.backend.styleFactory.service.EmpleadoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de empleados (estilistas).
 * Expone endpoints CRUD bajo la ruta {@code /empleados}.
 */
@RestController
@RequestMapping("/empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    /**
     * Lista estilistas activos para el catálogo público de reservas.
     */
    @GetMapping("/catalogo")
    public ResponseEntity<List<EmpleadoCatalogoDTO>> catalogoPublico() {
        return ResponseEntity.ok(empleadoService.findCatalogoActivos());
    }

    /**
     * Obtiene la lista de todos los empleados.
     *
     * @return Lista de empleados.
     */
    @GetMapping
    public ResponseEntity<List<EmpleadoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(empleadoService.findAll());
    }

    /**
     * Busca un empleado por su identificador.
     *
     * @param id ID del empleado.
     * @return Empleado encontrado o 404 si no existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDTO> obtenerPorId(@PathVariable Long id) {
        EmpleadoResponseDTO empleado = empleadoService.findById(id);
        if (empleado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(empleado);
    }

    /**
     * Crea un nuevo empleado asociado a un usuario existente.
     *
     * @param dto Datos del empleado.
     * @return Empleado creado con estado 201.
     */
    @PostMapping
    public ResponseEntity<EmpleadoResponseDTO> crear(@Valid @RequestBody EmpleadoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(empleadoService.save(dto));
    }

    /**
     * Actualiza los datos de un empleado.
     *
     * @param id  ID del empleado a modificar.
     * @param dto Nuevos datos del empleado.
     * @return Empleado actualizado o 404 si no existe.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDTO> actualizar(@PathVariable Long id,
                                                          @Valid @RequestBody EmpleadoRequestDTO dto) {
        EmpleadoResponseDTO actualizado = empleadoService.update(id, dto);
        if (actualizado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(actualizado);
    }

    /**
     * Desactiva un empleado (borrado lógico).
     *
     * @param id ID del empleado a desactivar.
     * @return Respuesta sin contenido (204).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        empleadoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}