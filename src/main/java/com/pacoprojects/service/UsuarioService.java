package com.pacoprojects.service;

import com.pacoprojects.model.Usuario;
import com.pacoprojects.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@AllArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public ResponseEntity<Usuario> getUsuario(Long id) {
        Optional<Usuario> usuarioOptional =  usuarioRepository.findById(id);
        if (usuarioOptional.isEmpty()) {
            throw new ResponseStatusException(BAD_REQUEST,"Usuario nao existe");
        }
        return ResponseEntity.ok(usuarioOptional.get());
    }

    public ResponseEntity<List<Usuario>> getUsuarios() {
        return ResponseEntity.ok(usuarioRepository.findAll());
    }
}
