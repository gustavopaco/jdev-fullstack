package com.pacoprojects.controller;

import com.pacoprojects.model.Usuario;
import com.pacoprojects.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://www.pacoprojects.com", "*"})
@RestController
@RequestMapping(path = "usuario")
@AllArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

/*    @GetMapping
    public ResponseEntity<?> init() {
        return ResponseEntity.ok("Ola REST Spring Boot");
    }*/

/*    @GetMapping
    public ResponseEntity<?> getUsuarioNome(
            @RequestParam(name = "nome", defaultValue = "Default User") String nome,
            @RequestParam(name = "salario", required = false) Double salario) {
        if (salario != null) {
            return ResponseEntity.ok("Ola REST Spring Boot, seu NOME eh: " + nome + " e voce tem um salario no valor de R$: " + salario);
        }
        return ResponseEntity.ok("Ola REST Spring Boot, seu NOME eh: " + nome);
    }*/

    @GetMapping(path = "{id}")
    public ResponseEntity<Usuario> getUsuario(@PathVariable(name = "id") Long id) {
        return usuarioService.getUsuario(id);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> getUsuarios() {
        return usuarioService.getUsuarios();
    }

    @PostMapping
    public ResponseEntity<Usuario> registerUsuario(@RequestBody Usuario usuario) {
        return usuarioService.registerUsuario(usuario);
    }

    @PutMapping
    public ResponseEntity<Usuario> updateUsuario(@RequestBody Usuario usuario) {
        return usuarioService.updateUsuario(usuario);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable(name = "id") Long id) {
        return usuarioService.deleteUsuario(id);
    }
}
