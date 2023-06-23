package org.fmera.springcloud.msvc.usuarios.usuarios.controller;

import org.fmera.springcloud.msvc.usuarios.models.entity.Usuario;
import org.fmera.springcloud.msvc.usuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    //Metodo para mostrar todos los usuarios
    @GetMapping("/usuarios")
    public List<Usuario> listar() {
        return usuarioService.listar();
    }

    //Se usa ResponseEntity para poder devolver un 404 en caso de que no exista el usuario de tipo <?> ya que no sabemos si
    //existe o no

    //Metodo para mostrar un usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id) {
        Optional<Usuario> usuarioOptional = usuarioService.porId(id);
        if (usuarioOptional.isPresent()) {
            return ResponseEntity.ok().body(usuarioOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    //Metodo para crear un usuario.
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Usuario usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.guardar(usuario));
    }

    //Metodo para editar un usuario por el id.
    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@RequestBody Usuario usuario, @PathVariable Long id) {
        Optional<Usuario> op = usuarioService.porId(id);
        if (op.isPresent()) {
            Usuario usuarioDB = op.get();
            usuarioDB.setNombre(usuario.getNombre());
            usuarioDB.setEmail(usuario.getEmail());
            usuarioDB.setPassword(usuario.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.guardar(usuarioDB));
        }
        return ResponseEntity.notFound().build();
    }

    //Metodo para eliminar un usuario por su id.
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Usuario> op = usuarioService.porId(id);
        if (op.isPresent()) {
            usuarioService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
