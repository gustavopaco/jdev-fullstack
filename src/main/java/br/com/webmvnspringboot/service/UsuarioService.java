package br.com.webmvnspringboot.service;

import br.com.webmvnspringboot.model.Usuario;
import br.com.webmvnspringboot.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public String getWelcome(String name) {
        return "Welcome to our API Rest service Mr(a) " + name + "!";
    }

    public String getDescription() {
        return "Welcome, you are at description Page.";
    }

    public List<Usuario> getUsuarios() {
        return usuarioRepository.findAll();
    }

    public void registerUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }
}
