package org.fmera.springcloud.msvc.cursos.services;

import org.fmera.springcloud.msvc.cursos.clients.UsuarioClientRest;
import org.fmera.springcloud.msvc.cursos.models.Usuario;
import org.fmera.springcloud.msvc.cursos.models.entity.Curso;
import org.fmera.springcloud.msvc.cursos.models.entity.CursoUsuario;
import org.fmera.springcloud.msvc.cursos.repositories.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CursoServiceImplementa implements CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioClientRest usuarioClientRest;

    @Override
    @Transactional(readOnly = true)
    public List<Curso> listar() {
        return cursoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> porId(Long id) {
        return cursoRepository.findById(id);
    }

    @Override
    @Transactional
    public Curso guardar(Curso curso) {
        return cursoRepository.save(curso);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        cursoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void eliminarCursoUsuarioPorId(Long id) {
        cursoRepository.eliminarCursoUsuarioPorId(id);
    }

    @Override
    @Transactional
    public Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> cursoOptional = cursoRepository.findById(cursoId);//Se busca el curso por id.
        if (cursoOptional.isPresent()) { //Si el curso existe.
            Usuario usuarioMsvc = usuarioClientRest.detalle(usuario.getId()); //Se obtiene el usuario del microservicio usuarios.
            Curso curso = cursoOptional.get();//Se obtiene el curso de la base de datos.
            CursoUsuario cursoUsuario = new CursoUsuario();//Se crea un nuevo objeto de tipo CursoUsuario.
            cursoUsuario.setUsuarioId(usuarioMsvc.getId());//Se asigna el id del usuario del microservicio usuarios.
            curso.addCursoUsuario(cursoUsuario);//Se agrega el usuario al curso.
            cursoRepository.save(curso);//Se guarda el curso.
            return Optional.of(usuarioMsvc);//Se retorna el usuario del microservicio usuarios.
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> cursoOptional = cursoRepository.findById(cursoId);//Se busca el curso por id.
        if (cursoOptional.isPresent()) {
            Usuario usuarioNuevoMsvc = usuarioClientRest.crear(usuario);//Se crea el usuario en el microservicio usuarios.
            Curso curso = cursoOptional.get();//Se obtiene el curso de la base de datos.
            CursoUsuario cursoUsuario = new CursoUsuario();//Se crea un nuevo objeto de tipo CursoUsuario.
            cursoUsuario.setUsuarioId(usuarioNuevoMsvc.getId());//Se asigna el id del usuario del microservicio usuarios.
            curso.addCursoUsuario(cursoUsuario);//Se agrega el usuario al curso.
            cursoRepository.save(curso);//Se guarda el curso.
            return Optional.of(usuarioNuevoMsvc);//Se retorna el usuario del microservicio usuarios.
        }

        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> cursoOptional = cursoRepository.findById(cursoId);//Se busca el curso por id.
        if (cursoOptional.isPresent()) {
            Usuario usuarioMsvc = usuarioClientRest.detalle(usuario.getId());//Se obtiene el usuario del microservicio usuarios.
            Curso curso = cursoOptional.get();//Se obtiene el curso de la base de datos.
            CursoUsuario cursoUsuario = new CursoUsuario();//Se crea un nuevo objeto de tipo CursoUsuario.
            cursoUsuario.setUsuarioId(usuarioMsvc.getId());//Se asigna el id del usuario del microservicio usuarios.
            curso.removeCursoUsuario(cursoUsuario);//Se elimina el usuario del curso.
            cursoRepository.save(curso);//Se guarda el curso.
            return Optional.of(usuarioMsvc);//Se retorna el usuario del microservicio usuarios.
        }
        return Optional.empty();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> obtenerUsuariosPorCurso(Long id) {
        Optional<Curso> o = cursoRepository.findById(id);//Se busca el curso por id.
        if(o.isPresent()){
            Curso curso = o.get();//Se obtiene el curso de la base de datos.
            if(!curso.getCursoUsuarios().isEmpty()){
                List<Long> ids = curso.getCursoUsuarios()
                        .stream().map(cu->cu.getUsuarioId()) //Cada cursoUsuario se convierte en un id de usuario
                        .collect(Collectors.toList());//Se obtienen los ids de los usuarios del curso y lo convertimso en una lista
                List<Usuario> usuarios = usuarioClientRest.obtenerUsuariosPorCurso(ids);//Se obtienen los usuarios del microservicio usuarios.
                curso.setUsuarios(usuarios);//Se asignan los usuarios al curso.
            }
            return Optional.of(curso);
        }
        return Optional.empty();
    }
}
