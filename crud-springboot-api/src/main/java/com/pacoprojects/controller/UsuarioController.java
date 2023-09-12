package com.pacoprojects.controller;

import com.pacoprojects.dto.UsuarioDTO;
import com.pacoprojects.model.Usuario;
import com.pacoprojects.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = {"http://www.pacoprojects.com", "*"}) /* Liberando CORS para todos metodos do UsuarioController */
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
    public ResponseEntity<UsuarioDTO> getUsuario(@PathVariable(name = "id") Long id) {
        return usuarioService.getUsuario(id);
    }

//  Criando versionamento de end-points |
//  Pode ser feito alterando Path=v2/{id} OU
//  Adicionando um HEADERs que deve ser passado no Request para acionar metodo,
//  na Versao 2 o Metodo PODE fazer outras funcionalidades, alem de Buscar dados do Usuario
    @GetMapping(path = "{id}", headers = {"X-API-Version=v2"})
    public ResponseEntity<UsuarioDTO> getUsuariov2(@PathVariable(name = "id") Long id) {
        return usuarioService.getUsuario(id);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> getUsuarios() {
        return usuarioService.getUsuarios();
    }

    @PostMapping
//    @CrossOrigin(origins = {"http://www.sistemadocliente10poderegistrar.com", "*"})
    public ResponseEntity<Usuario> registerUsuario(@RequestBody Usuario usuario) {
        return usuarioService.registerUsuario(usuario);
    }

    @PutMapping
//    @CrossOrigin(origins = {"http://www.sistemadocliente11podeatulizar.com", "*"})
    public ResponseEntity<Usuario> updateUsuario(@RequestBody Usuario usuario) {
        return usuarioService.updateUsuario(usuario);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable(name = "id") Long id) {
        return usuarioService.deleteUsuario(id);
    }
}
