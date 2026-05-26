package com.backend.styleFactory.repository;

import com.backend.styleFactory.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByCorreo(String correo);

    List<Usuario> findByEstadoTrue();

    boolean existsByCorreo(String correo);
}
