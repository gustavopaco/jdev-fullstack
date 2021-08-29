package br.com.curso.webmvnspringbootmicroservicos.controller;

import br.com.curso.webmvnspringbootmicroservicos.model.Role;
import br.com.curso.webmvnspringbootmicroservicos.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = "role")
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<Role>> getRoles() {
        return roleService.getRoles();
    }
}
