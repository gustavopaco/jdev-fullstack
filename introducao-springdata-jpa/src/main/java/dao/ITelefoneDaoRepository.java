package dao;

import model.Telefone;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITelefoneDaoRepository extends CrudRepository<Telefone,Long> {
}
