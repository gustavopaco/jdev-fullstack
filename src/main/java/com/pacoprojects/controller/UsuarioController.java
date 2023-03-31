package com.pacoprojects.controller;

import com.pacoprojects.dto.UsuarioDto;
import com.pacoprojects.dto.UsuarioUpdateDto;
import com.pacoprojects.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioDto>> getAllUsuarios() {
        return ResponseEntity.ok(usuarioService.getAllUsuarios());
    }

    @GetMapping(path = "listByName/{nome}")
    public ResponseEntity<List<UsuarioDto>> getAllUsuariosByName(@PathVariable(name = "nome") String nome) {
        return ResponseEntity.ok(usuarioService.getAllUsuariosByName(nome));
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<UsuarioDto> getUsuarioById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(usuarioService.getUsuarioById(id));
    }

    @PutMapping
    public ResponseEntity<UsuarioUpdateDto> updateUsuario(@Valid @RequestBody UsuarioUpdateDto usuarioUpdateDto, BindingResult bindingResult) {
        return ResponseEntity.ok(usuarioService.updateUsuario(usuarioUpdateDto, bindingResult));
    }

    @DeleteMapping(path = "{id}")
    public void deleteUsuario(@PathVariable(name = "id") Long id) {
        usuarioService.deleteUsuario(id);
    }
}
