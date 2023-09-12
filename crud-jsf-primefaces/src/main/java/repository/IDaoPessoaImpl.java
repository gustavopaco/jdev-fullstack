package repository;

import dao.GenericDao;
import model.Pessoa;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

@Model
public class IDaoPessoaImpl implements IDaoPessoa{

    @Inject
    private EntityManager entityManager;

    @Inject
    private GenericDao genericDao;

    @SuppressWarnings(value = "unchecked")
    @Override
    public void deletarPessoaEmCascata(Pessoa pessoa) throws Exception {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
        if (pessoa.getTelefonePessoas() != null && !pessoa.getTelefonePessoas().isEmpty()){
            entityManager.createQuery("delete from TelefonePessoa t where t.pessoa.id=:pid").setParameter("pid", pessoa.getId()).executeUpdate();
        }
        if (pessoa.getCartoesCredito() != null && !pessoa.getCartoesCredito().isEmpty()){
            entityManager.createQuery("delete from CartaoCredito c where c.pessoa.id=:pid").setParameter("pid", pessoa.getId()).executeUpdate();
        }
            transaction.commit();
        genericDao.deleteByID(Pessoa.class,pessoa.getId());
    }

    @SuppressWarnings(value = "unchecked")
    @Override
    public List<Pessoa> listaPessoaSalarios() throws Exception {
        return entityManager.createQuery("select p from Pessoa p order by p.salario").getResultList();
    }
}
