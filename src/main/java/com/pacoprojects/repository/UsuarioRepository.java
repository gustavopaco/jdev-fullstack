package com.pacoprojects.repository;

import com.pacoprojects.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findUsuarioByUsername(String username);

    List<Usuario> findAllByNomeContainsIgnoreCase(String nome);
}
