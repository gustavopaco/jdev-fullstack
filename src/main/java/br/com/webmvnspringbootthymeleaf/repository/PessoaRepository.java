package br.com.webmvnspringbootthymeleaf.repository;

import br.com.webmvnspringbootthymeleaf.model.Pessoa;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    /*** ABAIXO - Exemplos de Busca paginada por @Query ***/

    @Query(value = "select p from Pessoa p where lower(p.nome) like %?1%")
    Page<Pessoa> findPessoaByNamePageQuery(String nome, Pageable pageable);

    @Query(value = "select p from Pessoa p where p.sexo = ?1")
    Page<Pessoa> findPessoaBySexoPageQuery(String sexo, Pageable pageable);

    @Query(value = "select p from Pessoa p where lower(p.nome) like %?1% and p.sexo = ?2")
    Page<Pessoa> findPessoaByNameSexoPageQuery(String nome, String sexo, Pageable pageable);

    /*** ABAIXO - Exemplos de busca paginada por Objeto Criteria ***/

    default Page<Pessoa> findPessoaByNamePage(String nome, Pageable pageable) {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(nome);

        /* CONFIGURANDO pesquisa para consulta por parte do nome no banco de dados, igual like com SQL */
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny()
                .withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

        /* Une o Objeto com valor e configuracao para consultar */
        Example<Pessoa> pessoaExample = Example.of(pessoa, exampleMatcher);

        return findAll(pessoaExample, pageable);
    }

    default Page<Pessoa> findPessoaBySexoPage(String sexo, Pageable pageable) {
        Pessoa pessoa = new Pessoa();
        pessoa.setSexo(sexo);

        /* CONFIGURANDO pesquisa para consulta por parte do nome no banco de dados, igual like com SQL */
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny()
                .withMatcher("sexo", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

        /* Une o Objeto com valor e configuracao para consultar */
        Example<Pessoa> pessoaExample = Example.of(pessoa, exampleMatcher);

        return findAll(pessoaExample, pageable);
    }

    default Page<Pessoa> findPessoaByNameSexoPage(String nome, String sexo, Pageable pageable) {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(nome);
        pessoa.setSexo(sexo);

        /* CONFIGURANDO pesquisa para consulta por parte do nome no banco de dados, igual like com SQL */
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
                .withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("sexo", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

        /* Une o Objeto com valor e configuracao para consultar */
        Example<Pessoa> pessoaExample = Example.of(pessoa, exampleMatcher);

        return findAll(pessoaExample, pageable);
    }
}
