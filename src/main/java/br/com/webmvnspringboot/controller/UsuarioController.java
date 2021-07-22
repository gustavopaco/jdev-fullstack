package br.com.webmvnspringboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/usuario")
public class UsuarioController {

    @GetMapping(path = "{name}")
    public String getWelcome(@PathVariable String name) {
        return "Welcome to our API Rest service Mr(a) " + name + "!";
    }

    @GetMapping(path = "description")
    public String getDescription() {
        return "Welcome, you are at description Page.";
    }
}
