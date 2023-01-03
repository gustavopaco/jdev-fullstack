package com.pacoprojects.revjsbtapi.controller;

import com.pacoprojects.revjsbtapi.model.Usuario;
import com.pacoprojects.revjsbtapi.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping
public class Greetings {

    private final UsuarioRepository usuarioRepository;

    @GetMapping(path = "{nome}")
    public String olaMundo(@PathVariable String nome) {
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setIdade((int)(Math.random()*10));
        usuarioRepository.save(usuario);
        return "Curso Spring Boot!  Quantidade de usuarios no banco: " + usuarioRepository.count();
    }

    @GetMapping(path = "mundo")
    public String retornoOlaMundo() {
        return "Ola Mundo!";
    }

}
