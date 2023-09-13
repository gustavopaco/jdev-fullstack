package com.pacoprojects.service;

import com.pacoprojects.model.Usuario;
import com.pacoprojects.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class ImpUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOptional = usuarioRepository.findUsuarioByLogin(login);

        if (usuarioOptional.isEmpty()) {
            throw new UsernameNotFoundException("Usuario nao foi encontrado.");
        }
        return new User(usuarioOptional.get().getUsername(),usuarioOptional.get().getPassword(), usuarioOptional.get().getAuthorities());
    }
}
