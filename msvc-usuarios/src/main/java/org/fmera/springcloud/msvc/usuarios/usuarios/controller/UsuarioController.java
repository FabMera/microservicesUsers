package org.fmera.springcloud.msvc.usuarios.usuarios.controller;

import jakarta.validation.Valid;
import org.fmera.springcloud.msvc.usuarios.models.entity.Usuario;
import org.fmera.springcloud.msvc.usuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    //Metodo para mostrar todos los usuarios
    @GetMapping
    public List<Usuario> listar() {
        return usuarioService.ListAllUsers();
    }

    //Se usa ResponseEntity para poder devolver un 404 en caso de que no exista el usuario de tipo <?> ya que no sabemos si
    //existe o no

    //Metodo para mostrar un usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id) {
        Optional<Usuario> usuarioOptional = usuarioService.ListUserForId(id);
        if (usuarioOptional.isPresent()) {
            return ResponseEntity.ok().body(usuarioOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    //Metodo para crear un usuario.
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody @Valid Usuario usuario, BindingResult binding) {
        ResponseEntity<Map<String, Object>> response = validEmail(usuario);
        if (response != null) return response;
        if (binding.hasErrors()) {
            return validar(binding);
        }
        Usuario usuarioDB = usuarioService.SaveUser(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDB);
    }

    //Metodo para editar un usuario por el id.
    //BindingResult es para validar los campos del usuario  y debe ir justo despues del objeto que se va a validar.
    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@RequestBody @Valid Usuario usuario, BindingResult binding, @PathVariable Long id) {
        if (binding.hasErrors()) {
            return validar(binding);
        }
        Optional<Usuario> op = usuarioService.ListUserForId(id);
        if (op.isPresent()) {
            Usuario usuarioDB = op.get();
            if (!usuario.getEmail().equalsIgnoreCase(usuarioDB.getEmail())) {
                ResponseEntity<Map<String, Object>> response = validEmail(usuario);
                if (response != null) return response;
            }
            usuarioDB.setNombre(usuario.getNombre());
            usuarioDB.setEmail(usuario.getEmail());
            usuarioDB.setPassword(usuario.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.SaveUser(usuarioDB));
        }
        return ResponseEntity.notFound().build();
    }

    //Metodo para eliminar un usuario por su id.
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Usuario> op = usuarioService.ListUserForId(id);
        if (op.isPresent()) {
            usuarioService.DeleteUser(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    //Metodo para validar los campos del usuario, se usa un map para guardar los errores y devolverlos en el response.EXTRAER METODO IDE.
    //iteramos los errores y los guardamos en el map errores con el campo y el mensaje de error correspondiente a cada campo.
    private static ResponseEntity<Map<String, String>> validar(BindingResult binding) {
        Map<String, String> errores = new HashMap<>();
        binding.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }

    //Metodo para validar un email ya existente.
    private ResponseEntity<Map<String, Object>> validEmail(Usuario usuario) {
        if (usuarioService.FindUserForEmail(usuario.getEmail()).isPresent()) {
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "El email ya existe");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        return null;
    }

}
