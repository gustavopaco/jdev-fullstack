package br.com.curso.webmvnspringbootmicroservicos.repository;

import br.com.curso.webmvnspringbootmicroservicos.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query(value = "select u from Usuario u where u.username = ?1")
    Usuario findUsuarioByLogin(String username);

    @Query(value = "select case when count(u) > 0 then true else false end from Usuario u where u.id <> ?1 and u.username = ?2")
    boolean existsUsuarioLogin(Long id, String username);

    @Query(value = "select u from Usuario u where upper(u.nome) like %?1%")
    Page<Usuario> findUsuarioByName(String nome, Pageable pageable);
}
