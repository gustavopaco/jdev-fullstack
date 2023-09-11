package repository;

import model.Pessoa;
import model.TelefonePessoa;

import java.util.List;

public interface IDaoTelefone {

    List<TelefonePessoa> getTelefoneListByUserID(Long id);


}
