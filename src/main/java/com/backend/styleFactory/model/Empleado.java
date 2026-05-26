package com.backend.styleFactory.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "empleados") // Opcional, para definir el nombre de la tabla
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmpleado;

    // Aquí van los demás atributos que necesites (nombre, celular, etc.)
    // Ejemplo:
    // private String nombre;

    // Constructor vacío (Obligatorio para JPA)
    public Empleado() {
    }

    // Constructor con parámetros
    public Empleado(Long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    // Getters y Setters
    public Long getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }
}