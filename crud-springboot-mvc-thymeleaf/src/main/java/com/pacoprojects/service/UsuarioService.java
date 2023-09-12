package com.pacoprojects.service;

import com.pacoprojects.model.Usuario;
import com.pacoprojects.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findUserByLogin(username);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario nao foi encontrado");
        }

        return new User(usuario.getLogin(), usuario.getPassword(), usuario.isEnabled(),
                usuario.isAccountNonExpired(), usuario.isCredentialsNonExpired(), usuario.isAccountNonLocked(),
                usuario.getAuthorities());
    }
}
