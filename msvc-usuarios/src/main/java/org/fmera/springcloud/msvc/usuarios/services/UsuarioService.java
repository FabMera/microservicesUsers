package org.fmera.springcloud.msvc.usuarios.services;

import org.fmera.springcloud.msvc.usuarios.models.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    List<Usuario>ListAllUsers();
    Optional<Usuario> ListUserForId(Long id);
    Usuario SaveUser(Usuario usuario);
    void DeleteUser(Long id);

    List<Usuario> ListUsersForIds(List<Long> ids);
    Optional<Usuario> FindUserForEmail(String email);
}
