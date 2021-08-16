package br.com.curso.webmvnspringbootmicroservicos.service;

import br.com.curso.webmvnspringbootmicroservicos.model.Telefone;
import br.com.curso.webmvnspringbootmicroservicos.model.Usuario;
import br.com.curso.webmvnspringbootmicroservicos.repository.TelefoneRepository;
import br.com.curso.webmvnspringbootmicroservicos.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TelefoneService {

    private final TelefoneRepository telefoneRepository;
    private final UsuarioRepository usuarioRepository;

    public ResponseEntity<?> getTelefones(Long usuarioID) {
        List<Telefone> telefones = telefoneRepository.findTelefoneByUsuarioId(usuarioID);

        return ResponseEntity.ok(telefones);
    }

    public ResponseEntity<?> getTelefone(Long telefoneID) {
        Optional<Telefone> telefone = telefoneRepository.findById(telefoneID);

        if (telefone.isEmpty()) {
            return ResponseEntity.badRequest().body("Telefone nao encontrado");
        }
        return ResponseEntity.ok(telefone.get());
    }

    public ResponseEntity<?> addTelefone(Long usuarioID, Telefone telefone, BindingResult bindingResult) {
        List<String> strings = new ArrayList<>();

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> strings.add(objectError.getDefaultMessage()));
            return ResponseEntity.badRequest().body(strings);
        }

        Optional<Usuario> usuario = usuarioRepository.findById(usuarioID);

        usuario.ifPresentOrElse(telefone::setUsuario, () -> {
            throw new IllegalStateException();
        });
        telefoneRepository.save(telefone);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> updateTelefone(Telefone telefone, BindingResult bindingResult) {
        List<String> strings = new ArrayList<>();

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> strings.add(objectError.getDefaultMessage()));
            return ResponseEntity.badRequest().body(strings);
        }

        Optional<Telefone> telefoneConsultado = telefoneRepository.findById(telefone.getId());

        if (telefoneConsultado.isEmpty()) {
            return ResponseEntity.badRequest().body("Telefone nao existe");
        }

        BeanUtils.copyProperties(telefone, telefoneConsultado.get(), "id");
        Telefone telefoneAtualizado = telefoneRepository.save(telefoneConsultado.get());
        return ResponseEntity.ok(telefoneAtualizado);
    }

    public ResponseEntity<Void> deleteTelefone(Long telefoneID) {
        Optional<Telefone> telefone = telefoneRepository.findById(telefoneID);

        if (telefone.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        telefoneRepository.delete(telefone.get());
        return ResponseEntity.noContent().build();
    }
}
