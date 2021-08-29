package br.com.curso.webmvnspringbootmicroservicos.service;

import br.com.curso.webmvnspringbootmicroservicos.model.Role;
import br.com.curso.webmvnspringbootmicroservicos.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public ResponseEntity<List<Role>> getRoles() {
        return ResponseEntity.ok(roleRepository.findAll());
    }
}
