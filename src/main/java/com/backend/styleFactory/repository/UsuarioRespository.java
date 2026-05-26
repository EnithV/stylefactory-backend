package com.backend.styleFactory.repository;

import com.backend.styleFactory.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRespository extends JpaRepository<Usuario, Long> {
}
