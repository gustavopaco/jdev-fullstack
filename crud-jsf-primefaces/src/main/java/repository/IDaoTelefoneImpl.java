package repository;

import dao.GenericDao;
import model.Pessoa;
import model.TelefonePessoa;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

@Model
public class IDaoTelefoneImpl implements IDaoTelefone{

    @Inject
    private EntityManager entityManager;

    @SuppressWarnings(value = "unchecked")
    @Override
    public List<TelefonePessoa> getTelefoneListByUserID(Long id) {
        return entityManager.createQuery("select t from TelefonePessoa t where t.pessoa.id =:tid").setParameter("tid",id).getResultList();
    }
}
