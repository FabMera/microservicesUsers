package org.fmera.springcloud.msvc.usuarios.services;

import org.fmera.springcloud.msvc.usuarios.clients.CursoClientRest;
import org.fmera.springcloud.msvc.usuarios.models.entity.Usuario;
import org.fmera.springcloud.msvc.usuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImplementacion implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoClientRest cursoClientRest;

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> ListAllUsers() {
        return (List<Usuario>) usuarioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> ListUserForId(Long id) {
        return usuarioRepository.findById(id);
    }

    @Transactional
    @Override
    public Usuario SaveUser(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Transactional
    @Override
    public void DeleteUser(Long id) {
        usuarioRepository.deleteById(id);
        cursoClientRest.eliminarCursoUsuarioPorId(id);
    }

    @Override
    public List<Usuario> ListUsersForIds(List<Long> ids) {
        return (List<Usuario>) usuarioRepository.findAllById(ids);
    }
    @Override
    public Optional<Usuario> FindUserForEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
}
