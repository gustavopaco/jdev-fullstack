package com.pacoprojects.revjsbtapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class Greetings {

    @GetMapping(path = "{nome}")
    public String olaMundo(@PathVariable String nome) {
        return "Curso Spring " + nome;
    }

    @GetMapping(path = "mundo")
    public String retornoOlaMundo() {
        return "Ola Mundo!";
    }

}
