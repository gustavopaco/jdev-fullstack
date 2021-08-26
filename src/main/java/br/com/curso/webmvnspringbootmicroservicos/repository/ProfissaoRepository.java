package br.com.curso.webmvnspringbootmicroservicos.repository;

import br.com.curso.webmvnspringbootmicroservicos.model.Profissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfissaoRepository extends JpaRepository<Profissao, Long> {

}
