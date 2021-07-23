package br.com.webmvnspringboot.service;

import br.com.webmvnspringboot.model.Usuario;
import br.com.webmvnspringboot.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    public void deleteUsuario(Long id) {
        Optional<Usuario> user = usuarioRepository.findById(id);
        usuarioRepository.delete(user.orElseThrow(() -> new IllegalStateException("User not found")));
    }

    public Usuario findUsuario(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.orElseThrow(() -> new IllegalStateException("User not found"));
    }

    public Usuario updateUsuario(Usuario usuario) {
        Optional<Usuario> user = usuarioRepository.findById(usuario.getId());
        user.orElseThrow(() -> new IllegalStateException("User not found to update"));
        return usuarioRepository.saveAndFlush(usuario);
    }

    @Transactional
    public Usuario updateUsuario2(Usuario usuario) {
        Optional<Usuario> userOptional = usuarioRepository.findById(usuario.getId());
        Usuario user = userOptional.orElseThrow(() -> {
            throw new IllegalStateException("Error while processing user to update");
        });

        if (usuario.getName() != null && !usuario.getName().isEmpty() && !Objects.equals(usuario.getName(),user.getName())) {
            user.setName(usuario.getName());
        }

        if (usuario.getDob() != null && !Objects.equals(usuario.getDob(),user.getDob())) {
            user.setDob(usuario.getDob());
        }
        return user;
    }

    public List<Usuario> findByPartName(String name) {
        return usuarioRepository.findByPartName(name.trim().toLowerCase());
    }
}
