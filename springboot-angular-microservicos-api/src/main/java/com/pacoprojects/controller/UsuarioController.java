package com.pacoprojects.controller;

import com.pacoprojects.dto.UsuarioDto;
import com.pacoprojects.dto.UsuarioUpdateDto;
import com.pacoprojects.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<Page<UsuarioDto>> getAllUsuarios() {
        return ResponseEntity.ok(usuarioService.getAllUsuarios());
    }

    @GetMapping(path = "page")
    public ResponseEntity<Page<UsuarioDto>> getUsuariosPages(@RequestParam(name = "nome", required = false) String nome,
                                                             @PageableDefault(size = 5, sort = {"nome"}) Pageable pageable) {
        return ResponseEntity.ok(usuarioService.getUsuariosPages(nome, pageable));
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
