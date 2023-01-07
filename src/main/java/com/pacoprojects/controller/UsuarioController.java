package com.pacoprojects.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "usuario")
public class UsuarioController {

//    @GetMapping
//    public ResponseEntity<?> init() {
//        return ResponseEntity.ok("Ola REST Spring Boot");
//    }

    @GetMapping
    public ResponseEntity<?> getUsuarioNome(
            @RequestParam(name = "nome", defaultValue = "Default User") String nome,
            @RequestParam(name = "salario", required = false) Double salario) {
        if (salario != null) {
            return ResponseEntity.ok("Ola REST Spring Boot, seu NOME eh: " + nome + " e voce tem um salario no valor de R$: " + salario);
        }
        return ResponseEntity.ok("Ola REST Spring Boot, seu NOME eh: " + nome);
    }
}
