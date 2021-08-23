package br.com.curso.webmvnspringbootmicroservicos.controller;

import br.com.curso.webmvnspringbootmicroservicos.dto.UsuarioDTOGET;
import br.com.curso.webmvnspringbootmicroservicos.model.Usuario;
import br.com.curso.webmvnspringbootmicroservicos.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

//@EnableCaching /* IMPORTANT: Notacao pode ser utilizada a nivel de camada Applicacao(*), Controller e ate Service */
//@CrossOrigin /* IMPORTANT: Notacao pode ser utilizada em camada WebSecurity(*), Controller e ate Service */
/*TODO: Implementar consulta do usuario pelo Token(metodo de quebrar token) e nao por usuario vindo do Formulario.
*  nos metodos PUT, POST, DELETE */
@AllArgsConstructor
@RestController
@RequestMapping(path = "usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping(path = "v1")
    @Deprecated
    public ResponseEntity<List<Usuario>> getUsuarios() {
        return usuarioService.getUsuarios();
    }

    // IMPORTANT: Supondo que o carregamento de usuarios seja um processo lento e queremos deixar a lista em Cache
    @GetMapping(path = "v2")
    public ResponseEntity<List<UsuarioDTOGET>> getUsuarios(HttpServletRequest request) {
        return usuarioService.getUsuarios(request);
    }

    @GetMapping(path = "{id}", headers = "X-API-Version=v1")
    public ResponseEntity<Usuario> getUsuario(@PathVariable Long id) {
        return usuarioService.getUsuario(id);
    }

    @GetMapping(path = "{id}", headers = "X-API-Version=v2")
    public ResponseEntity<Usuario> getUsuario(@PathVariable Long id, HttpServletRequest request) {
        return usuarioService.getUsuario(id);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> getUsuarioByName(@RequestParam String nome) {
        return usuarioService.getUsuarioByName(nome);
    }

    @PostMapping
    public ResponseEntity<?> addUsuario(@Valid @RequestBody Usuario usuario, BindingResult bindingResult,
                                        HttpServletRequest request, HttpServletResponse response) {
        return usuarioService.addUsuario(usuario, bindingResult, request, response);
    }

    @PutMapping
    public ResponseEntity<?> updateUsuario(@Valid @RequestBody Usuario usuario, BindingResult bindingResult, HttpServletRequest request) {
        return usuarioService.updateUsuario(usuario, bindingResult);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        return usuarioService.deleteUsuario(id);
    }
}
