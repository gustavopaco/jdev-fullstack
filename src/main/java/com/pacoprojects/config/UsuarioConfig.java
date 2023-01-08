package com.pacoprojects.config;

import com.pacoprojects.model.Usuario;
import com.pacoprojects.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class UsuarioConfig {

    @Bean
    CommandLineRunner commandLineRunner(UsuarioRepository usuarioRepository) {
        return args -> {
            Usuario user = new Usuario(
                    "gustavopaco",
                    "Gustavo Paco",
                    "123"
            );
            Usuario user2 = new Usuario(
                    "augusto",
                    "Augusto Paco",
                    "123"
            );
            Usuario user3 = new Usuario(
                    "Jose",
                    "Jose",
                    "123"
            );
            Usuario user4 = new Usuario(
                    "maria",
                    "Maria",
                    "123"
            );
            usuarioRepository.saveAll(List.of(user,user2,user3,user4));
        };
    }
}
