package br.com.curso.webmvnspringbootmicroservicos.controller;

import br.com.curso.webmvnspringbootmicroservicos.model.Usuario;
import br.com.curso.webmvnspringbootmicroservicos.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = "usuario")
public class UsuarioController {

    private UsuarioService usuarioService;

    @GetMapping
    ResponseEntity<List<Usuario>> getUsuarios() {
        return usuarioService.getUsuarios();
    }

    @GetMapping(path = "{id}")
    ResponseEntity<Usuario> getUsuario(@PathVariable Long  id) {
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
