package br.com.curso.webmvnspringbootmicroservicos.repository;

import br.com.curso.webmvnspringbootmicroservicos.model.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TelefoneRepository extends JpaRepository<Telefone, Long> {

    @Query(value = "select t from Telefone t where t.usuario.id = ?1")
    List<Telefone> findTelefoneByUsuarioId(Long id);
}
