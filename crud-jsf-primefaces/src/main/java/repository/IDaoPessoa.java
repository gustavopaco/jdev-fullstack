package repository;

import model.Pessoa;

import java.util.List;

public interface IDaoPessoa {

    void deletarPessoaEmCascata(Pessoa pessoa) throws Exception;

    List<Pessoa> listaPessoaSalarios() throws Exception;
}
