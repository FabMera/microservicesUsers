package org.fmera.springcloud.msvc.cursos.services;

import org.fmera.springcloud.msvc.cursos.models.Usuario;
import org.fmera.springcloud.msvc.cursos.models.entity.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoService {
    List<Curso> listar();
    Optional<Curso> porId(Long id);
    Curso guardar(Curso curso);
    void eliminar(Long id);


    // Se utiliza el modelo Usuario del microservicio usuarios para no tener que crear un modelo Usuario en este microservicio.
    //Metodo para asignar un usuario a un curso.
    Optional<Usuario> asignarUsuario(Usuario usuario,Long cursoId);

    //Metodo para crear un usuario en un curso.
    Optional<Usuario> crearUsuario(Usuario usuario,Long cursoId);

    //Metodo para eliminar un usuario de un curso no se elimina el usuario solo se desasigna del curso.
    Optional<Usuario> eliminarUsuario(Usuario usuario,Long cursoId);
}
