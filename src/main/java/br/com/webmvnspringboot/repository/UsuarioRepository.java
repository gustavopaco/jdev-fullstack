package br.com.webmvnspringboot.repository;

import br.com.webmvnspringboot.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query(value = "select u from Usuario u where lower(trim(u.name)) like %?1% ")
    List<Usuario> findByPartName(String name);
}
