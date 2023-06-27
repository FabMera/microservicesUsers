package org.fmera.springcloud.msvc.cursos.repositories;

import org.fmera.springcloud.msvc.cursos.models.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
}
