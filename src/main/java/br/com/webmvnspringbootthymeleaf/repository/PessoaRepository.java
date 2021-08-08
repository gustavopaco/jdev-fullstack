package br.com.webmvnspringbootthymeleaf.repository;

import br.com.webmvnspringbootthymeleaf.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    @Query(value = "select p from Pessoa p where lower(p.nome) like %?1% or lower(p.sobrenome) like %?1%")
    List<Pessoa> findByName(String nome);

    @Query(value = "select p from Pessoa p where p.sexo like %?1%")
    List<Pessoa> findBySexo(String sexo);

    @Query(value = "select p from Pessoa p where (lower(p.nome) like %?1% or lower(p.sobrenome) like %?1%) and p.sexo = ?2")
    List<Pessoa> findByNameSexo(String nome, String sexo);
}
