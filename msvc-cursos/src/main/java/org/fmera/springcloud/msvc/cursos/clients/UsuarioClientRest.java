package org.fmera.springcloud.msvc.cursos.clients;

import org.fmera.springcloud.msvc.cursos.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//Esta clase se comunica con el microservicio de usuarios para poder obtener los datos de los usuarios.
//Se usa FeignClient para poder comunicarse con el microservicio de usuarios.
//Se usa @FeignClient(name = "msvc-usuarios", url = "localhost:8081") para indicar el nombre del microservicio y la url donde se encuentra.
@FeignClient(name = "msvc-usuarios", url = "localhost:8081")
public interface UsuarioClientRest {

    @GetMapping("/{id}")
    Usuario detalle(@PathVariable Long id);

    @PostMapping("/usuarios")
    Usuario crear(@RequestBody Usuario usuario);


}
