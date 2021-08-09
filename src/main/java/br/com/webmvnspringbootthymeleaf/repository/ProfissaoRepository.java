package br.com.webmvnspringbootthymeleaf.repository;

import br.com.webmvnspringbootthymeleaf.model.Profissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfissaoRepository extends JpaRepository<Profissao,Long> {
}
