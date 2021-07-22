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
    public String getWelcome(@PathVariable String name) {
        return usuarioService.getWelcome(name);
    }

    @GetMapping(path = "description")
    public String getDescription() {
        return usuarioService.getDescription();
    }

    @GetMapping(path = "usuarios")
    public List<Usuario> getUsuarios() {
        return usuarioService.getUsuarios();
    }

    @PostMapping(path = "register")
    public void registerUsuario(@RequestBody Usuario usuario) {
        usuarioService.registerUsuario(usuario);
    }
}
