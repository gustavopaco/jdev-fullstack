package com.pacoprojects.controller;

import com.pacoprojects.model.Usuario;
import com.pacoprojects.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping(path = "sds")
public class Greetings {

    private final UsuarioRepository usuarioRepository;

    @GetMapping(path = "{nome}")
    public String olaMundo(@PathVariable String nome) {
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setIdade((int)(Math.random()*10));
        usuarioRepository.save(usuario);
        return "Curso Spring Boot!  Quantidade de usuarios no banco: " + usuarioRepository.count();
    }

    @GetMapping(path = "mundo")
    public String retornoOlaMundo() {
        return "Ola Mundo!";
    }

    @GetMapping(path = "list")
    public ResponseEntity<?> listarUsuarios() {
        List<Usuario> usuarioList = usuarioRepository.findAll();
        if (!usuarioList.isEmpty()) {
            return ResponseEntity.ok(usuarioList);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nao contem usuarios cadastrados");
    }

    @PostMapping
    public ResponseEntity<?> addUsuario(@RequestBody Usuario usuario, BindingResult bindingResult) {
        try {
            usuarioRepository.save(usuario);
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao cadastrar usuario");
        }
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable Long id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

        if (usuarioOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario nao foi encontrado");
        }
        usuarioRepository.delete(usuarioOptional.get());
        return ResponseEntity.ok("Usuario deletado com sucesso");
    }

    @GetMapping(path = "search")
    public ResponseEntity<Usuario> findUsuarioById(@RequestParam(name = "userID") Long userID) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(userID);
        if (usuarioOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario Nao encontrado");
        }
        return ResponseEntity.ok(usuarioOptional.get());
    }

    @PutMapping
    public ResponseEntity<Usuario> updateUsuario(@RequestBody Usuario usuario) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuario.getId());

        if (usuarioOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Usuario nao foi encontrado para atualizacao");
        }
        usuarioRepository.save(usuario);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping(path = "searchName")
    public ResponseEntity<List<Usuario>> findByNamePart(@RequestParam(name = "namePart") String namePart) {
        List<Usuario> usuarioList = usuarioRepository.findByNomeContainingIgnoreCase(namePart.strip());

        if (usuarioList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nao foi encontrado usuarios com esse nome.");
        }
        return ResponseEntity.ok(usuarioList);
    }
}
