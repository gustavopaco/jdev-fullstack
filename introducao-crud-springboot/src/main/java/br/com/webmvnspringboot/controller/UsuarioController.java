package br.com.webmvnspringboot.controller;

import br.com.webmvnspringboot.model.Usuario;
import br.com.webmvnspringboot.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/usuario/")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping(path = "{name}")
    public String getWelcome(@PathVariable(name = "name") String name) {
        return usuarioService.getWelcome(name);
    }

    @GetMapping(path = "description")
    public String getDescription() {
        return usuarioService.getDescription();
    }

    @GetMapping(path = "usuario", params = "id")
    public Usuario findUsuario(@RequestParam(value = "id") Long id) {
        return usuarioService.findUsuario(id);
    }

    @GetMapping(path = "usuarios")
    public List<Usuario> getUsuarios() {
        return usuarioService.getUsuarios();
    }

    @GetMapping(path = "containsName", params = "name")
    public List<Usuario> findByPartName(@RequestParam(value = "name") String name) {
        return usuarioService.findByPartName(name);
    }

    @PostMapping(path = "register")
    public Usuario registerUsuario(@RequestBody Usuario usuario) {
        return usuarioService.registerUsuario(usuario);
    }

    @PutMapping(path = "update")
    public Usuario updateUsuario(@RequestBody Usuario usuario) {
        return usuarioService.updateUsuario(usuario);
    }

    @DeleteMapping(path = "{id}")
    public void deleteUsuario(@PathVariable(name = "id") Long id) {
        usuarioService.deleteUsuario(id);
    }
}
