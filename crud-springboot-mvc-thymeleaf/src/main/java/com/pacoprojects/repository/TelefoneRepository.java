package com.pacoprojects.repository;

import com.pacoprojects.model.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TelefoneRepository extends JpaRepository<Telefone, Long> {

    @Query(value = "select t from Telefone t where t.pessoa.id = ?1")
    List<Telefone> findByPessoaID(Long pessoaID);
}
