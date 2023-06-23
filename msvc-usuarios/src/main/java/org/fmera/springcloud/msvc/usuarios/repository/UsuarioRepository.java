package org.fmera.springcloud.msvc.usuarios.repository;

import org.fmera.springcloud.msvc.usuarios.models.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
