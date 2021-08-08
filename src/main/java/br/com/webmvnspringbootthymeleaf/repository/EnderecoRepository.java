package br.com.webmvnspringbootthymeleaf.repository;

import br.com.webmvnspringbootthymeleaf.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
