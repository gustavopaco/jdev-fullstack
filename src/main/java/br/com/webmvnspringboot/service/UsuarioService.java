package br.com.webmvnspringboot.service;

import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    public String getWelcome(String name) {
        return "Welcome to our API Rest service Mr(a) " + name + "!";
    }

    public String getDescription() {
        return "Welcome, you are at description Page.";
    }
}
