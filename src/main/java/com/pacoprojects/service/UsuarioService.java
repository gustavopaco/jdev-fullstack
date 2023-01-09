package com.pacoprojects.service;

import com.pacoprojects.dto.SucessoDTO;
import com.pacoprojects.model.Usuario;
import com.pacoprojects.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

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

    public ResponseEntity<Usuario> registerUsuario(Usuario usuario) {
        usuario.adicionarTelefones(usuario.getTelefones().get(0));
        Usuario user = usuarioRepository.save(usuario);
        return ResponseEntity.ok(user);
    }

    @Transactional
    public ResponseEntity<Usuario> updateUsuario(Usuario usuario) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuario.getId());
        if (usuarioOptional.isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, "Usuario nao foi encontrado!");
        }
        BeanUtils.copyProperties(usuario,usuarioOptional.get());
        return ResponseEntity.ok(usuarioOptional.get());
    }

    public ResponseEntity<?> deleteUsuario(Long id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isEmpty()) {
            throw new ResponseStatusException(BAD_REQUEST, "Usuario nao foi encontrado");
        }
        usuarioRepository.deleteById(id);
        return ResponseEntity.ok(new SucessoDTO("Usuario deletado com sucesso"));
    }
}
