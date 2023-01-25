package com.pacoprojects.service;

import com.pacoprojects.util.BCryptUtil;
import com.pacoprojects.dto.SucessoDTO;
import com.pacoprojects.model.Usuario;
import com.pacoprojects.repository.UsuarioRepository;
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

    private final BCryptUtil bCryptUtil;

    public ResponseEntity<Usuario> getUsuario(Long id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isEmpty()) {
            throw new ResponseStatusException(BAD_REQUEST, "Usuario nao existe");
        }
        return ResponseEntity.ok(usuarioOptional.get());
    }

    public ResponseEntity<List<Usuario>> getUsuarios() {
        return ResponseEntity.ok(usuarioRepository.findAll());
    }

    public ResponseEntity<Usuario> registerUsuario(Usuario usuario) {

        /* Utilizando List*/
        usuario.adicionarTelefonesAoUsuario(usuario);

        /* Utilizando Hash Set so eh possivel cadastrar um telefone em um Usuario*/
//        usuario.getTelefones().forEach(usuario::adicionarTelefones);

        /* Reescrevendo senha criptografando a mesma*/
        usuario.setSenha(bCryptUtil.password().encode(usuario.getPassword()));

        Usuario user = usuarioRepository.save(usuario);
        return ResponseEntity.ok(user);
    }

    /* UPDATE UTILIZANDO HASHSET*/
/*    public ResponseEntity<Usuario> updateUsuario(Usuario usuario) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuario.getId());
        if (usuarioOptional.isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, "Usuario nao foi encontrado!");
        }
        usuario.getTelefones().forEach(usuario::adicionarTelefones);
        usuarioOptional.get().getTelefones().clear();
        usuarioOptional.get().adicionarTelefones(usuario.getTelefones().stream().iterator().next());
        BeanUtils.copyProperties(usuario,usuarioOptional.get(),"telefones");
        usuarioRepository.save(usuarioOptional.get());
        return ResponseEntity.ok(usuarioOptional.get());
    }*/

    /* UPDATE UTILIZANDO LIST */
    public ResponseEntity<Usuario> updateUsuario(Usuario usuario) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuario.getId());
        if (usuarioOptional.isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, "Usuario nao foi encontrado!");
        }
        usuario.adicionarTelefonesAoUsuario(usuario);
//        System.out.println(System.currentTimeMillis()); VERIFICACAO do Tempo de Execucao para limpar lista e adicionar novos dados
        usuarioOptional.get().getTelefones().clear();
        usuarioOptional.get().getTelefones().addAll(usuario.getTelefones());
//        System.out.println(System.currentTimeMillis()); VERIFICACAO do Tempo de Execucao para limpar lista e adicionar novos dados

        if (!usuario.getPassword().equals(usuarioOptional.get().getPassword())) {
            if (usuario.getPassword() != null && !bCryptUtil.password().matches(usuario.getPassword(), usuarioOptional.get().getPassword())) {
                usuarioOptional.get().setSenha(bCryptUtil.password().encode(usuario.getPassword()));
            }
        }

        BeanUtils.copyProperties(usuario, usuarioOptional.get(), "id", "telefones", "password");
        usuarioRepository.save(usuarioOptional.get());
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
