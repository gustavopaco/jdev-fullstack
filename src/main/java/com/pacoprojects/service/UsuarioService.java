package com.pacoprojects.service;

import com.pacoprojects.dto.SucessoDTO;
import com.pacoprojects.model.Usuario;
import com.pacoprojects.repository.UsuarioRepository;
import com.pacoprojects.security.JWTUtilService;
import com.pacoprojects.util.BCryptUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@AllArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final BCryptUtil bCryptUtil;
    private final JWTUtilService jwtUtilService;

    @Cacheable(cacheNames = "getUsuario", key = "#id")
    public ResponseEntity<Usuario> getUsuario(Long id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isEmpty()) {
            throw new ResponseStatusException(BAD_REQUEST, "Usuario nao existe");
        }
        return ResponseEntity.ok(usuarioOptional.get());
    }

    //  Carregando lista de usuario sera colocado em Cache para nao ficar consultando ao banco toda vez,
    //  e so sera feita nova consulta quando algum dado da lista for alterada, ao dar INSERT, UPDATE ou DELETE de algum usuario
    @Cacheable(cacheNames = "getUsuarios")
    public ResponseEntity<List<Usuario>> getUsuarios() {
//            Segura o codigo por 6 segundos simulando um processo lento.
//            Thread.sleep(6000);
        return ResponseEntity.ok(usuarioRepository.findAll());
    }

    @CacheEvict(cacheNames = {"getUsuarios", "getUsuario"}, allEntries = true)
    public ResponseEntity<Usuario> registerUsuario(Usuario usuario) {

        /* Utilizando List*/
        usuario.adicionarTelefonesAoUsuario(usuario);

        /* Utilizando Hash Set so eh possivel cadastrar um telefone em um Usuario*/
//        usuario.getTelefones().forEach(usuario::adicionarTelefones);

        /* Reescrevendo senha criptografando a mesma*/
        usuario.setSenha(bCryptUtil.password().encode(usuario.getPassword()));

        /* Gerando Jwt ao registrar novo usuario e deixa-lo logado ao sistema*/
        Map<String, Object> map = jwtUtilService.generateJwt(usuario.getUsername(), TimeUnit.DAYS.toMillis(2));

        String basicToken = map.get("basicToken").toString();
        usuario.setJwt(basicToken);

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
    @CachePut(cacheNames = {"getUsuarios", "getUsuario"}, key = "#usuario.id")
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

    @CacheEvict(cacheNames = {"getUsuarios", "getUsuario"}, allEntries = true)
    public ResponseEntity<?> deleteUsuario(Long id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isEmpty()) {
            throw new ResponseStatusException(BAD_REQUEST, "Usuario nao foi encontrado");
        }
        usuarioRepository.deleteById(id);
        return ResponseEntity.ok(new SucessoDTO("Usuario deletado com sucesso"));
    }
}
