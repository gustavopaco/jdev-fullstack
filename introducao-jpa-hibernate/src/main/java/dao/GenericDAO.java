package dao;

import util.HibernateConexaoUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class GenericDAO<E> {
    private EntityManager entityManager = HibernateConexaoUtil.getEntityManager();

    public void insertEntity(E object) {

        // Obtem a transacao do entityManager
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        // Grava no Banco o Usuario || EntityManager => preparedstatement.executeUpdate()
        entityManager.persist(object);

        // Comita as alteracoes no banco || EntityTransaction => connection.commit() ou connect.rollback()
        transaction.commit();
    }

    public E getEntityByObject(E object) { // Pesquisa por um objeto de acordo com o ID informado e retorna ele preenchido completamente

        Object id = HibernateConexaoUtil.getPrimaryKey(object);

        E e = (E) entityManager.find(object.getClass(), id);

        return e;
    }

    public E getEntityByLong(Class<E> object, Long id_usuario) { // Pesquisa por um objeto de acordo com o ID informado e retorna ele preenchido completamente

        E e = entityManager.find(object, id_usuario);

        return e;
    }

    public E updateEntity(E object) { // Salva ou Atualiza

        // Obtem a transacao do entityManager
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        // Grava no Banco o Usuario || EntityManager => preparedstatement.executeUpdate()
        E e = entityManager.merge(object);

        // Comita as alteracoes no banco || EntityTransaction => connection.commit() ou connect.rollback()
        transaction.commit();

        return e;
    }

    public void removeEntityByObject(E object) {

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.remove(object);

        transaction.commit();
    }

    public void removeEntityByID(Class<E> object, Long id_object) {

        E entity = getEntityByLong(object, id_object);

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

//        entityManager.createNativeQuery("delete from " +
//                object.getSimpleName().toLowerCase() + " where id = " + id_object).executeUpdate();
        entityManager.remove(entity); // Remove usuario pelo ID e Classe que ele pertence

        transaction.commit(); // Salva alteracoes
    }

    public List<E> getEntities(Class<E> object) {

        List<E> entities = entityManager.createQuery(" from " + object.getName()).getResultList();

        return entities;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
