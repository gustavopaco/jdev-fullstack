package br.com.curso.webmvnspringbootmicroservicos.controller;

import br.com.curso.webmvnspringbootmicroservicos.model.Usuario;
import br.com.curso.webmvnspringbootmicroservicos.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

//@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping(path = "usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping(path = "v1")
    @Deprecated
    ResponseEntity<List<Usuario>> getUsuarios() {
        return usuarioService.getUsuarios();
    }

    @GetMapping(path = "v2")
    ResponseEntity<List<Usuario>> getUsuarios(HttpServletRequest request) {
        return usuarioService.getUsuarios();
    }

    @GetMapping(path = "{id}", headers = "X-API-Version=v1")
    ResponseEntity<Usuario> getUsuario(@PathVariable Long  id) {
        return usuarioService.getUsuario(id);}

    @GetMapping(path = "{id}", headers = "X-API-Version=v2")
    ResponseEntity<Usuario> getUsuario(@PathVariable Long  id, HttpServletRequest request) {
        return usuarioService.getUsuario(id);}

    @PostMapping
    ResponseEntity<?> addUsuario(@Valid @RequestBody Usuario usuario, BindingResult bindingResult) {
        return usuarioService.addUsuario(usuario, bindingResult);
    }

    @PutMapping
    ResponseEntity<?> updateUsuario(@Valid @RequestBody Usuario usuario, BindingResult bindingResult) {
        return usuarioService.updateUsuario(usuario, bindingResult);
    }

    @DeleteMapping(path = "{id}")
    ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        return usuarioService.deleteUsuario(id);
    }
}
